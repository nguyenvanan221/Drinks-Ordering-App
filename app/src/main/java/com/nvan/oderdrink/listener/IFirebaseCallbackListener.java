package com.nvan.oderdrink.listener;

public interface IFirebaseCallbackListener<T> {
    void onFirebaseLoadSucces(T items);

    void onFirebaseLoadFailed(String message);
}
