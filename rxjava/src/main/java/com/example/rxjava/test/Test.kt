package com.example.rxjava.test

import com.example.rxjava.Emitter
import com.example.rxjava.Observable
import com.example.rxjava.ObservableOnSubscribe
import com.example.rxjava.Observer
import com.example.rxjava.operation.Function

fun main(){
    Observable.create(object : ObservableOnSubscribe<String>{
        override fun subscribe(emitter: Emitter<String>) {
            emitter.onNext("aa")
        }
    }).map<String>(object : Function<String, String>{
        override fun apply(t: String): String {
            return "${t}bb"
        }
    }).subscribeObserver(object : Observer<String>{
        override fun onNext(t: String) {
            println(t)
        }

        override fun onSubscribe() {
        }

        override fun onError(e: Throwable?) {
        }

        override fun onComplete() {
        }

    })
}