package com.example.rxjava;

/**
 * 具体的被观察者
 */
public class ObservableCreate<T> extends Observable {
    private ObservableOnSubscribe<T> source;

    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(Observer observer) {
        // 在这里已经观察者和被观察者关联成功了
        observer.onSubscribe();

        // 创建发射器
        CreateEmitter createEmitter = new CreateEmitter(observer);
        source.subscribe(createEmitter);
    }

    /**
     * 发射器相当于做了一个中转
     * 这些发射器的东西由程序员来调用
     * @param <T>
     */
    private static final class CreateEmitter<T> implements Emitter<T>{
        private Observer<T> observer;

        public CreateEmitter(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T t) {
            observer.onNext(t);
        }

        @Override
        public void onError(Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }
}
