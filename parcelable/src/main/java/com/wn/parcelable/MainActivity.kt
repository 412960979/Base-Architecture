package com.wn.parcelable

import android.content.Intent
import android.os.Bundle
import com.wn.parcelable.*

@ViewBindingLayout(R.layout.activity_main, "ActivityMainBinding")
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = User("wei", "ning", 16)
        Intent().putExtra("user", user)
        val myClass = MyClass(ExternalClass(5))
        Intent().putExtra("myClass", myClass)
    }

}