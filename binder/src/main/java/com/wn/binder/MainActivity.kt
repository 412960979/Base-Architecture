package com.wn.binder

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mIBookManager: IBookManager? = null

    private val mConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 获取远程服务Binder的代理：service
            // service 转化为 本地的 mIBookManager 接口
            // asInterface 返回的是代理类 IBookManager.Stub.Proxy
            // 通过 IBookManager 接口依赖
            mIBookManager = IBookManager.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i("test", "server disconnected")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.setOnClickListener {
            test()
            test2()
        }
    }

    // 发起绑定服务
    private fun bindBookService(): Boolean {
        val intent = Intent()
        // 指定 action，要绑定的服务类，在服务端的清单文件里定义
        intent.action = "com.wn.binder.test.AidlService"
        // 指定 包名，服务端的包名
        intent.setPackage("com.wn.binder")
        startService(intent)
        return bindService(intent, mConnection, BIND_AUTO_CREATE)
    }
    // end 发起绑定服务

    private fun test() {
        // 调用 绑定服务
        val b = bindBookService()
        if (b) {
            Log.i("test", "SUCCESS")
        } else {
            Log.i("test", "FAILED")
        }
    }

    fun test2() {
        Log.d("test", "test2")
        if (mIBookManager == null) {
            Log.d("test", "未绑定服务")
            return
        }
        try {
            // 发起 远程调用
            val res = mIBookManager!!.add(1, 1)
            Log.d("test", "res = $res")
        } catch (e: RemoteException) {
            Log.d("test", "e = $e")
            e.printStackTrace()
        }
    }

//    fun test3() {
//        Log.d("test", "test3")
//        if (mIBookManager == null) {
//            Log.d("test", "未绑定服务")
//            return
//        }
//        try {
//            // 发起 远程调用
//            val res = mIBookManager!!.wrong_add(1)
//            Log.d("test", "res = $res")
//        } catch (e: RemoteException) {
//            Log.d("test", "e = " + e.toString())
//            e.printStackTrace()
//        }
//    }
}