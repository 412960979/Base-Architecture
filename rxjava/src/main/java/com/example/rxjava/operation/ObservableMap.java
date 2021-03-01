package com.example.rxjava.operation;

import com.example.rxjava.ObservableSource;
import com.example.rxjava.Observer;

public class ObservableMap<T,U> extends AbstractObservableWithUpStream<T,U> {
    private final Function<T,U> function;

    public ObservableMap(ObservableSource source, Function<T,U> function) {
        super(source);

        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer observer) {
        source.subscribeObserver(new MapObservable(observer, function));
    }

    static final class MapObservable<T,U> extends BaseFunctionObservable<T,U>{
        private final Function<T,U> mapper;

        public MapObservable(Observer<U> observer, Function<T,U> mapper) {
            super(observer);

            this.mapper = mapper;
        }

        @Override
        public void onNext(T t) {
            U apply = mapper.apply(t);
            // 把结果往下游传递
            observer.onNext(apply);
        }
    }
}
