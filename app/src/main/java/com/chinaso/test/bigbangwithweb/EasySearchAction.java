/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */
package com.chinaso.test.bigbangwithweb;

import android.content.Context;
import android.content.Intent;

import com.chinaso.bigbang.core.action.Action;


/**
 * Created by yanxu on 2018/8/6.
 */
public class EasySearchAction implements Action {

    public static EasySearchAction create() {
        return new EasySearchAction();
    }

    @Override
    public void start(Context context, String text) {
        Intent detailIntent = new Intent(context, WebViewActivity.class);
        detailIntent.putExtra("search_words", text);
        context.startActivity(detailIntent);
    }
}
