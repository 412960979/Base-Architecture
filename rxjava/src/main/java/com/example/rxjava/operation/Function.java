package com.example.rxjava.operation;

/**
 * 事件变换
 * @param <T> 上游事件的结果
 * @param <R> 下游
 */
public interface Function<T,R> {
    // 交给用户去重写
    R apply(T t);
}
