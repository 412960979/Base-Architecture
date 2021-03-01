package com.example.rxjava;

/**
 * 抽象被观察者
 */
public interface ObservableSource {
    // 订阅 绑定Observable和Observer的关联
    void subscribeObserver(Observer observer);

}
