package com.chinaso.test.bigbangwithweb;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.chinaso.bigbang.core.BigBangActivity;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.ArrayList;
import java.util.List;


/**
 * js桥接
 * Created by yanxu on 2018/8/6.
 */
public class JSEngine {

    private static final String TAG = "JSEngine";

    protected final WebView mWebView;
    protected Context mContext;
    List<Term> arrayList;

    public JSEngine(Context context, WebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    /**
     * 获取js传递过来的长按文字
     * 进行大爆炸操作
     *
     * @param content
     */
    @JavascriptInterface
    public void getContent(final String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //进行分词，第一次加载耗时
                arrayList = StandardTokenizer.segment(content.replace(" ", ""));
                Intent intent = new Intent();
                ArrayList<String> text = new ArrayList<>();
                for (int i = 0; i < arrayList.size(); i++) {
                    text.add(arrayList.get(i).word);
                }
                intent.putStringArrayListExtra("bigbangText", text);
                intent.setClass(mContext, BigBangActivity.class);
                mContext.startActivity(intent);
            }
        }).start();

    }

}