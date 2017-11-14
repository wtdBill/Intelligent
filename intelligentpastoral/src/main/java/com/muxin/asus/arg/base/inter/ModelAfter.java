package com.muxin.asus.arg.base.inter;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/20
 * Description:
 */
public interface ModelAfter<T> {
    void success(T t);
    void error();
}
