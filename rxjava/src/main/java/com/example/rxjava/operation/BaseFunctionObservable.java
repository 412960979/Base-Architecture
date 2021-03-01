package com.example.rxjava.operation;

import com.example.rxjava.Observer;

public abstract class BaseFunctionObservable<T,U> implements Observer<T> {
    protected final Observer<U> observer;

    // 参数就是下游的observer
    public BaseFunctionObservable(Observer<U> observer) {
        this.observer = observer;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
