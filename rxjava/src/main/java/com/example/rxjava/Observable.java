package com.example.rxjava;

import com.example.rxjava.operation.Function;
import com.example.rxjava.operation.ObservableMap;

public abstract class Observable<T> implements ObservableSource {

    @Override
    public void subscribeObserver(Observer observer) {
        // 把这个功能留给各种不同的Observable去处理，比如map,flatMap等（通过模板方法）
        subscribeActual(observer);
    }

    // 模板方法
    protected abstract void subscribeActual(Observer observer);

    // 创建具体被观察者
    public static <T> Observable create(ObservableOnSubscribe<T> source){
        return new ObservableCreate<>(source);
    }

    public <U> Observable map(Function<T,U> function){
        return new ObservableMap<>(this, function);
    }
}
