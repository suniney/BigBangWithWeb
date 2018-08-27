package com.chinaso.bigbang.core.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;

/**
 * Created by yanxu on 2018/8/6.
 */

public class HTTPRequest {
    private static final String SPLIT_CHAR = " ";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    /**
     * 使用get方式请求分词服务
     *
     * @param text 原始中文字符串
     * @return 分词词组
     */
    public static void getSplitChar(final String text, final IResponse response) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                String result = null;
                HttpURLConnection conn = null;
                String utf8string = "";
                try {
                    utf8string = URLEncoder.encode(text,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                StringBuilder urlBuilder = new StringBuilder("https://api.ltp-cloud.com/analysis/")
                        .append('?').append("api_key").append('=').append("i2N0R4W7D8ifZDfAgF3KFzFhIT6SJkCM2EjUon3E")
                        .append('&').append("text").append('=').append(utf8string)
                        .append('&').append("pattern").append('=').append("ws")
                        .append('&').append("format").append('=').append("plain");
                String url = urlBuilder.toString();
                //此处用于证书识别出现问题可绕过证书请求
                try {
//                    //  直接通过主机认证
//                    HostnameVerifier hv = new HostnameVerifier() {
//                        public boolean verify(String urlHostName, SSLSession session) {
//                            return true;
//                        }
//                    };
//                    //  配置认证管理器
//                    javax.net.ssl.TrustManager[] trustAllCerts = {new TrustAllTrustManager()};
//                    SSLContext sc = SSLContext.getInstance("SSL");
//                    SSLSessionContext sslsc = sc.getServerSessionContext();
//                    sslsc.setSessionTimeout(0);
//                    sc.init(null, trustAllCerts, null);
//                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                    //证书问题报错
//                    conn.setRequestMethod("POST");
//                    conn.setSSLSocketFactory(context.getSocketFactory());
//                    conn.setHostnameVerifier(new HostnameVerifier() {
//                        @Override
//                        public boolean verify(String hostname, SSLSession session) {
//                            return true;
//                        }
//                    });
                    //  激活主机认证
//                    HttpsURLConnection.setDefaultHostnameVerifier(hv);


                    // 利用string url构建URL对象
                    URL mURL = new URL(url);
                    conn = (HttpURLConnection) mURL.openConnection();

                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(10000);
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        InputStream is = conn.getInputStream();
                        result = getStringFromInputStream(is);
                        if (response != null) {
                            response.finish(result == null ? null : result.split(SPLIT_CHAR));
                        }
                    } else {
                        if (response != null){
                            response.failure("访问失败" + responseCode + ':' + conn.getResponseMessage());
                        }
                    }
                } catch (Exception e) {
                    if (response != null){
                        response.failure(e.toString());
                    }
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        });
    }

    /**
     * 读取流,返回字符串
     * @param is
     * @return
     * @throws IOException
     */
    private static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }
}
