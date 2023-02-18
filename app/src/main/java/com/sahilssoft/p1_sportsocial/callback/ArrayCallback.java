package com.sahilssoft.p1_sportsocial.callback;

import java.util.List;

public interface ArrayCallback<T> extends BaseCallback{
    void onData(List<T> list);
}
