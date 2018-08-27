package com.chinaso.bigbang.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by yanxu on 2018/8/6.
 */
public class BigBangActivity extends AppCompatActivity implements BigBangLayout.ActionListener {


    private BigBangLayout mLayout;
    private ImageView bigbang_close;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mLayout.reset();
        handleIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_bang);

        bigbang_close = (ImageView) findViewById(R.id.bigbang_close);
        bigbang_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLayout = (BigBangLayout) findViewById(R.id.bigbang);
        mLayout.setActionListener(this);
        if (BigBang.getItemSpace() > 0) mLayout.setItemSpace(BigBang.getItemSpace());
        if (BigBang.getLineSpace() > 0) mLayout.setLineSpace(BigBang.getLineSpace());
        if (BigBang.getItemTextSize() > 0) mLayout.setItemTextSize(BigBang.getItemTextSize());
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (intent!=null){
            final ArrayList<String> arrayList = intent.getStringArrayListExtra("bigbangText");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLayout.reset();
                            for (int i = 0; i < arrayList.size(); i++) {
                                mLayout.addTextItem(arrayList.get(i).replace("\n", ""));
                            }
                        }
                    });
                }
            }).start();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String selectedText = mLayout.getSelectedText();
        BigBang.startAction(this, BigBang.ACTION_BACK, selectedText);
        this.finish();

    }

    @Override
    public void onSearch(String text) {
        BigBang.startAction(this, BigBang.ACTION_SEARCH, text);
        this.finish();
    }

    @Override
    public void onShare(String text) {
        BigBang.startAction(this, BigBang.ACTION_SHARE, text);
        this.finish();
    }

    @Override
    public void onCopy(String text) {
        BigBang.startAction(this, BigBang.ACTION_COPY, text);
        this.finish();
    }

}
