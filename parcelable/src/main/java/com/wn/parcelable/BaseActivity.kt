package com.wn.parcelable

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.RuntimeException

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//         binding = ActivityMainBinding.inflate(layoutInflater)
        // setContentView(binding.root)
        setContentView()
    }

    private fun setContentView(){
        val clazz = this.javaClass
        // 获取正常布局文件注解
        val annotation = clazz.getAnnotation(Layout::class.java)

        // 获取ViewBinding注解
        val viewBindingAnnotation = clazz.getAnnotation(ViewBindingLayout::class.java)

        // 获取DataBinding注解
        val dataBindingAnnotation = clazz.getAnnotation(DataBindingLayout::class.java)

        when {
            annotation != null -> {
                // 设置正常布局
                setContentView(annotation.layout)
            }
            viewBindingAnnotation != null -> {
                // 设置ViewBinding布局
                val className = viewBindingAnnotation.layout_name
                val classPath = "com.wn.parcelable.databinding.$className"
                val bind = Class.forName(classPath)
                val method = bind.getDeclaredMethod("inflate", LayoutInflater::class.java)
                val viewBinding = method.invoke(null, layoutInflater) as ViewBinding
                setContentView(viewBinding.root)
            }
            dataBindingAnnotation != null -> {
                // 设置DataBinding布局
            }
            else -> {
                throw RuntimeException("请先添加布局注解")
            }
        }
    }
}