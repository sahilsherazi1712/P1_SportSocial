package com.sahilssoft.p1_sportsocial.callback;

public interface ObjectCallback<T> extends BaseCallback{
    void onData(T t);
}
