package com.example.rxjava.operation;

import com.example.rxjava.Observable;
import com.example.rxjava.ObservableSource;

/**
 * 装饰器类
 * @param <T>
 * @param <U>
 */
public abstract class AbstractObservableWithUpStream<T,U> extends Observable {
    protected final ObservableSource source;

    public AbstractObservableWithUpStream(ObservableSource source) {
        this.source = source;
    }
}
