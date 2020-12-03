package com.base.koltin.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect

suspend fun testListFlow() {
    (1..3).asFlow().collect {
        println(it)
    }

    (4..6).asFlow().collect {
        println(it)
    }
}


suspend fun main(args: Array<String>) {
    test2()
}

suspend fun test2(){
    println("开始")

    val res0 = GlobalScope.async {
        var i = 0
        withContext(Dispatchers.IO){
            for (index in 0..100){
                delay(1_00)
                i++
                println("${Thread.currentThread()} main 计算中：${i}")
            }

            i
        }
    }

    val res = GlobalScope.async{
        demo1()
    }
    // 两个协程都运行完才执行下面的代码
    println("最终的值：${res.await()}, ${res0.await()}")
    println("结束")
}

suspend fun demo1(): Int {
    var i = 0

    return withContext(Dispatchers.IO) {
        for (index in 0..100) {
            delay(1_00)
            i++
            println("${Thread.currentThread()} io 计算中：${i}")
        }


        i
    }
}