package com.chinaso.test.bigbangwithweb;

import android.app.Application;
import android.content.Context;

import com.chinaso.bigbang.core.BigBang;
import com.chinaso.bigbang.core.action.CopyAction;
import com.chinaso.bigbang.core.action.ShareAction;


public class MyApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initBigBangSetting();
    }

    /**
     * 初始化大爆炸相关设置
     */
    private void initBigBangSetting() {
        BigBang.registerAction(BigBang.ACTION_SEARCH, EasySearchAction.create());
        BigBang.registerAction(BigBang.ACTION_COPY, CopyAction.create());
        BigBang.registerAction(BigBang.ACTION_SHARE, ShareAction.create());
        //ItemSpace,LineSpace,ItemTextSize
        BigBang.setStyle(10, 0, 15);
    }

    public static Context getContext() {
        return sContext;
    }
}
