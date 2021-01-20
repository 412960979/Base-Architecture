package com.base.koltin.demo.proxy

import android.os.Handler
import android.util.Log
import java.lang.RuntimeException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

interface ApiService {
    fun print(): String
}

fun main() {
    val apiService = MyProxy.create<ApiService>()
//    val apiService = MyProxy.create(ApiService::class.java)

    try {
        apiService.print()
    } catch (e: Exception) {
        println(e.toString())
    }

    Handler().post {

    }
}

object MyProxy {
    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf(service),
            object : InvocationHandler {
                override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {
                    if (method.name == "print") {
                        println("这是动态代理执行的")

                        try {
                            Log.i("test", "damn it!!!")
                        } catch (e: RuntimeException) {
                            throw RuntimeException("这个代码只有在Android手机上才能执行")
                        }
                        return null
                    }
                    return method.invoke(this, args)
                }
            }
        ) as T
    }

    inline fun <reified T> create(): T {
        return Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf(T::class.java)
        ) { _, method, args ->
            if (method.name == "print") {
                val a = 55
                val b = a.div(5)
                println("这是动态代理执行的: $b")

                try {
                    Log.i("test", "damn it!!!")
                } catch (e: RuntimeException) {
                    throw RuntimeException("这个代码只有在Android手机上才能执行")
                }
                null
            }
            method.invoke(this, args)
        } as T
    }
}