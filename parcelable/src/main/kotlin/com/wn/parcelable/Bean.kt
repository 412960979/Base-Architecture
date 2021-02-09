package com.wn.parcelable

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import kotlinx.parcelize.WriteWith

@Parcelize
data class User(val firstName: String, val lastName: String, val age: Int): Parcelable


// 自定义 Parceler  如果系统不能直接支持您的类型，您可以为其写一个 Parceler 映射对象。
class ExternalClass(val value: Int)

object ExternalClassParceler : Parceler<ExternalClass> {
    override fun create(parcel: Parcel) = ExternalClass(parcel.readInt())

    override fun ExternalClass.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
    }
}

// Class-local parceler
//@Parcelize
//@TypeParceler<ExternalClass, ExternalClassParceler>()
//data class MyClass(val external: ExternalClass) : Parcelable
//
//// Property-local parceler
//@Parcelize
//class MyClass(@TypeParceler<ExternalClass, ExternalClassParceler>() val external: ExternalClass) :
//    Parcelable

// Type-local parceler
@Parcelize
class MyClass(val external: @WriteWith<ExternalClassParceler>() ExternalClass) : Parcelable