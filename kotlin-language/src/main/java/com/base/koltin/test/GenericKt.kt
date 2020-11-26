package com.base.koltin.test

/**
 * out对应Java的协变，只读
 * in对应Java的逆变，可写
 */

interface Production<out T> {
    fun produce(): T
}

interface Consumer<in T> {
    fun consume(item: T)
}

class A(s: Int) : Production<String> {
    constructor(s: Int, b: String) : this(s)

    override fun produce(): String {
        return "test";
    }

}

class B(b: Int) : Consumer<Int>{
    override fun consume(item: Int) {
        println(item)
    }

}

// 这种🈶又是返回值又是参数，就不用协变也不用逆变
interface ProductionConsumer<T> {
    fun produce(): T
    fun consume(item: T)
}

class Test{

    fun test() {
        val a = A(0)
        println(a.produce())

        val b = B(1)
        b.consume(5)
    }

    fun test1(){
        // 协变
        val list1: MutableList<out Number> = mutableListOf<Int>()
//        list1.add(1)// error 协变不能add

        // 逆变
        val list2: MutableList<in Float> = mutableListOf<Number>()
        list2.add(1f)
    }
}

// ===============================================
open class Food
open class FastFood : Food()
class Burger : FastFood()

class FoodStore : Production<Food> {
    override fun produce(): Food {
        println("Produce food")
        return Food()
    }
}

class FastFoodStore : Production<FastFood> {
    override fun produce(): FastFood {
        println("Produce food")
        return FastFood()
    }
}

class InOutBurger : Production<Burger> {
    override fun produce(): Burger {
        println("Produce burger")
        return Burger()
    }
}

/*
* ⬆️这个注释是解释上面的代码
* 1.汉堡提供者
* 现在，我们可以这样赋值：

val production1 : Production<Food> = FoodStore()
val production2 : Production<Food> = FastFoodStore()
val production3 : Production<Food> = InOutBurger()
很显然，汉堡商店属于是快餐商店，当然也属于食品商店。

因此，对于 out 泛型，我们能够将使用子类泛型的对象赋值给使用父类泛型的对象。
而如果像下面这样反过来使用子类 - Burger 泛型，就会出现错误，因为快餐（fastfood）和食品（food）商店不仅仅提供汉堡（burger）。

val production1 : Production<Burger> = FoodStore()  // Error
val production2 : Production<Burger> = FastFoodStore()  // Error
val production3 : Production<Burger> = InOutBurger()
* */

class Everybody : Consumer<Food> {
    override fun consume(item: Food) {
        println("Eat food")
    }
}

class ModernPeople : Consumer<FastFood> {
    override fun consume(item: FastFood) {
        println("Eat fast food")
    }
}

class American : Consumer<Burger> {
    override fun consume(item: Burger) {
        println("Eat burger")
    }
}

/*
 * ⬆️这个注释是解释上面的代码
 * 2.汉堡消费者
 * 现在，我们能够将 Everybody, ModernPeople 和 American 都指定给汉堡消费者（Consumer<Burger>）：

val consumer1 : Consumer<Burger> = Everybody()
val consumer2 : Consumer<Burger> = ModernPeople()
val consumer3 : Consumer<Burger> = American()
很显然这里美国的汉堡的消费者既是现代人，更是人类。

因此，对于 in 泛型，我们可以将使用父类泛型的对象赋值给使用子类泛型的对象。
同样，如果这里反过来使用父类 - Food 泛型，就会报错：

val consumer1 : Consumer<Food> = Everybody()
val consumer2 : Consumer<Food> = ModernPeople()  // Error
val consumer3 : Consumer<Food> = American()  // Error


根据以上的内容，我们还可以这样来理解什么时候用 in 和 out：

父类泛型对象可以赋值给子类泛型对象，用 in；
子类泛型对象可以赋值给父类泛型对象，用 out。

* */
// ===================================================

/**
 * 解决泛型擦除
 */
fun buildRequest(param:Any){
    // 第一种具体类型
    if (param is Int){

    }

    // 第二种List 使用 * 通配符
    if (param is List<*>){
//        param[0] is Int
    }
}