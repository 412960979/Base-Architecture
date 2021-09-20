package com.base.koltin.coroutines

import android.media.MediaParser
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main() {
    test1()
}

fun test1() {
    println("start test1")
    runBlocking {
        println(Thread.currentThread().name)
        delay(2000)
    }

    println("end test1")

    thread {
        runBlocking {
            println(Thread.currentThread().name)
        }
    }
}

fun test22() {
    runBlocking {
        val time = measureTimeMillis {
            async {}
        }
    }
}