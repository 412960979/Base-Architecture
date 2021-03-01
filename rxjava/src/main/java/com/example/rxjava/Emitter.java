package com.example.rxjava;

/**
 * 消息发射器
 */
public interface Emitter<T> {
    void onNext(T t);
    void onError(Throwable e);
    void onComplete();
}
