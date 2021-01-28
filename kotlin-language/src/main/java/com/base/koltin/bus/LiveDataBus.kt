package com.base.koltin.bus

import android.util.ArrayMap
import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.jvm.javaClass as javaClass1

class LiveDataBus private constructor() {
    // 处理黏性事件(View没有创建也能收到消息.发送事件之后再订阅该事件也能收到该事件)
    private val liveDataStickyMap: ArrayMap<String, BusMutableLiveData<Any>> = ArrayMap()
    // 处理普通事件(View创建了才能收到消息)
    private val liveDataMap: ArrayMap<String, MutableLiveData<Any>> = ArrayMap()

    companion object {
        val instance: LiveDataBus by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            LiveDataBus()
        }
    }

    fun <T> with(key: String): MutableLiveData<T> {
        if (!liveDataMap.containsKey(key)) {
            val mutableLiveData = MutableLiveData<T>()
            liveDataMap[key] = mutableLiveData as MutableLiveData<Any>
        }
        return liveDataMap[key] as MutableLiveData<T>
    }

    fun <T> withSticky(key: String): MutableLiveData<T> {
        if (!liveDataMap.containsKey(key)) {
            val mutableLiveData = BusMutableLiveData<T>()
            liveDataMap[key] = mutableLiveData as BusMutableLiveData<Any>
        }
        return liveDataMap[key] as MutableLiveData<T>
    }

    fun remove(key: String){
        if (liveDataMap.remove(key) == null){
            throw RuntimeException("请检查key是否正确")
        }
    }

    fun removeSticky(key: String){
        if (liveDataStickyMap.remove(key) == null){
            throw RuntimeException("请检查key是否正确")
        }
    }

    private inner class ObserverWrapper<T>(observer: Observer<in T>) : Observer<T>{
        private val observer = observer

        override fun onChanged(t: T) {
            if (isCallOnObserve()) {
                return;
            }
            observer.onChanged(t)
        }

        private fun isCallOnObserve(): Boolean {
            val stackTrace = Thread.currentThread().stackTrace
            if (stackTrace.isNotEmpty()) {
                for (element in stackTrace) {
                    if ("android.arch.lifecycle.LiveData" == element.className && "observeForever" == element.methodName) {
                        return true
                    }
                }
            }
            return false
        }
    }

    /**
     * hook源码，处理非粘性事件
     * view没有创建对象的时候不允许收到消息
     */
    private inner class BusMutableLiveData<T> : MutableLiveData<T>() {
        private val observerMap: MutableMap<Observer<*>, Observer<*>> = HashMap()

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)

            try {
                // 设置observer的version和LiveData一致
                hook(observer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun observeForever(observer: Observer<in T>) {
            if (!observerMap.containsKey(observer)) {
                observerMap[observer] = ObserverWrapper(observer)
            }
            super.observeForever(observerMap[observer] as Observer<in T>)
        }

        override fun removeObserver(@NonNull observer: Observer<in T>) {
            var realObserver: Observer<*>? = null
            realObserver = if (observerMap.containsKey(observer)) {
                observerMap.remove(observer)
            } else {
                observer
            }
            super.removeObserver(realObserver as Observer<in T>)
        }

        @Throws(Exception::class)
        private fun hook(observer: Observer<in T>) {
            // 获取livedata的class对象
            val classLiveData: Class<*> = LiveData::class.java
            // 获取   LiveData类的mObservers对象 （Map对象）的 Field对象
            val fieldObservers: Field = classLiveData.getDeclaredField("mObservers")
            // 将mObservers 的private设置为 public
            fieldObservers.isAccessible = true
            //  获取当前livedata的mObservers对象(map)
            val objectObservers: Any = fieldObservers.get(this)
            // 拿到mObservers（map）的class对象
            val classObservers: Class<*> = objectObservers.javaClass1
            // 通过map的class对象拿到get（）的method对象
            val methodGet: Method = classObservers.getDeclaredMethod("get", Any::class.java)
            methodGet.isAccessible = true
            // 通过map 的 get Method对象 拿到值 （Entry）  （arg1：map ，arg2：key ）
            val objectWrapperEntry: Any? = methodGet.invoke(objectObservers, observer)
            // 拿到wrapper
            var objectWrapper: Any? = null
            if (objectWrapperEntry is Map.Entry<*, *>) {
                objectWrapper = objectWrapperEntry.value
            }
            if (objectWrapper == null) {
                throw NullPointerException("Wrapper can not be bull!")
            }
            // 反射wrapper对象
            val classObserverWrapper: Class<*> = objectWrapper.javaClass1.superclass
            // 拿到wrapper的version
            val fieldLastVersion: Field = classObserverWrapper.getDeclaredField("mLastVersion")
            fieldLastVersion.isAccessible = true
            //get livedata's version
            val fieldVersion: Field = classLiveData.getDeclaredField("mVersion")
            fieldVersion.isAccessible = true
            val objectVersion: Any = fieldVersion.get(this)
            //set wrapper's version
            fieldLastVersion.set(objectWrapper, objectVersion)
        }
    }
}