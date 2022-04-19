package com.base.koltin.algorithm

fun main(){
    var a = 1
    var b = 2

    a = a xor b
    b = a xor b
    a = a xor b

    println("a=$a, b=$b")
}