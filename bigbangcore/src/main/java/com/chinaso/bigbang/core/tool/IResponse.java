package com.chinaso.bigbang.core.tool;

/**
 * Created by yanxu on 2018/8/6.
 */

public interface IResponse {
    void finish(String[] words);
    void failure(String errorMsg);
}
