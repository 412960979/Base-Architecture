package com.base.koltin

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun test1(){
    GlobalScope.launch {
        delay((1000L))
        println("world")
    }
    println("hello")
    Thread.sleep(2000L)
}

suspend fun testListFlow(){
    (1..3).asFlow().collect {
        println(it)
    }

    (4..6).asFlow().collect {
        println(it)
    }
}

suspend fun main(){
    test1()

//    testListFlow()
}