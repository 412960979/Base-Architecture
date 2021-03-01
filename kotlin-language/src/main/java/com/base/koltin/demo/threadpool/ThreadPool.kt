package com.base.koltin.demo.threadpool

import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max
import kotlin.math.min

/**
 * 最大线程数设置：
 * 如果是CPU密集型应用，则线程池大小设置为N+1
 * 如果是IO密集型应用，则线程池大小设置为2N+1
 * 参考资料：https://blog.csdn.net/qq_41648631/article/details/102871630
 */

// 获取cpu数量
val CPU_COUNT = Runtime.getRuntime().availableProcessors()

// 核心线程数大小
val corePoolSize = max(2, min(CPU_COUNT - 1, 4))

// 线程池最大容纳线程数
val maximumPoolSize = CPU_COUNT * 2 + 1

// 线程空闲后的存活时间
const val keepAliveTime = 30L

// 任务过多后，存储任务的一个阻塞队列
val synchronousQueue = SynchronousQueue<Runnable>()
val linkedBlockingQueue = LinkedBlockingQueue<Runnable>(100)

// 线程的创建工厂
val threadFactory = ThreadFactory {
    val mCount = AtomicInteger(1)

    Thread(it, "AdvancedAsyncTask #${mCount.getAndIncrement()}")
}

// 线程池任务满载后采取的任务拒绝策略
val rejectHandler = ThreadPoolExecutor.DiscardOldestPolicy()

fun main() {
//    testThreadPoolExecute()
//    testFixThreadPool()
    testCachedThreadPool()
//    testSingleThreadPool()
//    testScheduledThreadPool()
//    test()
}

/**
 * 测试线程池
 */
private fun threadPoolExecute(execute: () -> Executor) {
    println("核心线程数：${corePoolSize}, 最大线程数：${maximumPoolSize}")

    val threadPoolExecute = execute()

    for (i in 0 until 30) {
        val runnable = Runnable {
            Thread.sleep(1000)

            println("当前线程：${Thread.currentThread().name},  run: $i")
        }

        threadPoolExecute.execute(runnable)
    }
}

/**
 * 创建基本线程
 */
private fun testThreadPoolExecute() {
    threadPoolExecute {
        ThreadPoolExecutor(
            3, 5, 1, TimeUnit.SECONDS,
            linkedBlockingQueue
        )

        // 对象池创建，创建线程
//        ThreadPoolExecutor(
//            corePoolSize,
//            maximumPoolSize,
//            keepAliveTime,
//            TimeUnit.SECONDS,
//            synchronousQueue,
//            threadFactory,
//            rejectHandler
//        )
    }
}

/**
 * 定长线程池
 * 特点:
 * 参数为核心线程数,无非核心线程数，并且阻塞队列无界
 * 可控制线程最大并发数，超出的线程会在队列中等待
 */
private fun testFixThreadPool() {
    threadPoolExecute {
        Executors.newFixedThreadPool(corePoolSize)
    }
}

/**
 * 可缓存线程池
 * 特点：
 * 没有核心线程，只有非核心线程，并且每个非核心线程的等待时间为60S，采用SynchronousQueue队列
 * 如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
 * CachedThreadPool适用于有大量需要立即执行的耗时少的任务的情况
 */
private fun testCachedThreadPool() {
    threadPoolExecute {
        Executors.newCachedThreadPool()
    }
}

/**
 * 单线程的线程池
 * 特点：
 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
 */
private fun testSingleThreadPool() {
    threadPoolExecute {
        Executors.newSingleThreadExecutor()
    }
}

/**
 * 定时延时任务线程池
 * 特点：
 * 创建一个定长线程池，支持定时及周期性任务执行
 */
private fun testScheduledThreadPool() {
    val scheduledThreadPool = Executors.newScheduledThreadPool(corePoolSize)

    val runnable = Runnable {
        println("thread-name：${Thread.currentThread().name}, 这是延时任务线程池")
    }

    println("开始")
//    scheduledThreadPool.schedule(runnable,2,TimeUnit.SECONDS)
    //延迟5s后启动，每1s执行一次
    // scheduledThreadPool.scheduleAtFixedRate(runnable,5,1,TimeUnit.SECONDS);
    //启动后第一次延迟5s执行，后面延迟1s执行
    scheduledThreadPool.scheduleWithFixedDelay(runnable, 0, 2, TimeUnit.SECONDS);
}

private fun test() {
    val executor = Executors.newFixedThreadPool(6)

    var result1 = 0
    val runnable1 = Runnable {
        for (i in 0..10) {
            result1++
            println("r1  thread-name：${Thread.currentThread().name}, result=${result1}")
        }

        executor.shutdown()
    }
    executor.execute(runnable1)

    var result2 = 0
    val runnable2 = Runnable {
        for (i in 0..5) {
            result2++
            println("r2  thread-name：${Thread.currentThread().name}, result=${result2}")
        }

        executor.shutdown()
    }
    executor.execute(runnable2)


    while (true){
        if (executor.isTerminated) {
            println("任务执行结束 result1=${result1}, result2=${result2}")
            break
        }
    }
}


/**
 * 常见的阻塞队列
 * ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。
LinkedBlockingQueue ：一个由链表结构组成的有界阻塞队列。
PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。
DelayQueue：一个使用优先级队列实现的无界阻塞队列。
SynchronousQueue：一个不存储元素的阻塞队列。
LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。
LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。
 */






























