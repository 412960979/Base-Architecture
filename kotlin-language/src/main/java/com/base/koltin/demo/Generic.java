package com.base.koltin.demo;

import android.content.Context;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/**
 * 泛型测试
 *
 * 规则：
 * 要从泛型类取数据时，用extends协变；
 * 要往泛型类写数据时，用super逆变；
 * 既要取又要写，就不用通配符（即extends与super都不用）
 */
public class Generic {

    private void test1(Context context){
        // 协变  确定泛型的上界
        ArrayList<? extends TextView> views = new ArrayList<Button>();
//        views.add(new Button(context)); // error
//        views.add(new TextView(context));// error

        // 逆变  确定泛型的下界
        ArrayList<? super Button>  views1 = new ArrayList<TextView>();
        views1.add(new RadioButton(context));
    }
}

/**
 * Demo
 * 接口default关键字
 *    作用：Java8引入的接口虚拟扩展方法。是指，在接口内部包含了一些默认的方法实现（也就是接口中可以包含方法体，
 *         这打破了Java之前版本对接口的语法限制），从而使得接口在进行扩展的时候，不会破坏与接口相关的实现类代码。
 *    为什么要有这个特性：首先，之前的接口是个双刃剑，好处是面向抽象而不是面向具体编程，缺陷是，当需要修改接口时候，
 *         需要修改全部实现该接口的类，目前的java8之前的集合框架没有foreach方法，通常能想到的解决办法是在JDK里给相关的接口添加新的方法及实现。
 *         然而，对于已经发布的版本，是没法在给接口添加新方法的同时不影响已有的实现。所以引进的默认方法。
 *         他们的目的是为了解决接口的修改与现有的实现不兼容的问题。
 * @param <E>
 */
interface Stack<E>{
    public void push(E e);
    public E pop();
    public boolean isEmpty();

    /*
     * 假设有一个实例化Stack<Number>的对象stack，src有Iterable<Integer>与 Iterable<Float>；
     * 在调用pushAll方法时会发生type mismatch错误，因为Java中泛型是不可变的，Iterable<Integer>
     * 与 Iterable<Float>都不是Iterable<Number>的子类型。因此，应该这么写
     * @param src
     */
    public default void pushAll(Iterable<? extends E> src) {
        for (E e : src)
            push(e);
    }

    /*
     * 假设有一个实例化Stack<Number>的对象stack，dst为Collection<Object>；
     * 调用popAll方法是会发生type mismatch错误，因为Collection<Object>不是Collection<Number>的子类型。
     * 因而，应该这么写：
     * @param dst
     */
    public default void popAll(Collection<? super E> dst) {
        while (!isEmpty())
            dst.add(pop());
    }

    // java.util.Collections的copy方法(JDK1.7)完美地诠释了协变逆变
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        int srcSize = src.size();
        if (srcSize > dest.size())
            throw new IndexOutOfBoundsException("Source does not fit in dest");

        if (srcSize < 5 ||
                (src instanceof RandomAccess && dest instanceof RandomAccess)) {
            for (int i=0; i<srcSize; i++)
                dest.set(i, src.get(i));
        } else {
            ListIterator<? super T> di=dest.listIterator();
            ListIterator<? extends T> si=src.listIterator();
            for (int i=0; i<srcSize; i++) {
                di.next();
                di.set(si.next());
            }
        }
    }
}


