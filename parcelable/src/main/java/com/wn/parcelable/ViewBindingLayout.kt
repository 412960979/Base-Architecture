package com.wn.parcelable

@Target(AnnotationTarget.CLASS)
annotation class Layout(val layout: Int)

@Target(AnnotationTarget.CLASS)
annotation class ViewBindingLayout(val layout: Int, val layout_name: String)


@Target(AnnotationTarget.CLASS)
annotation class DataBindingLayout(val layout: Int, val layout_name: String)