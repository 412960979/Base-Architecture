package com.base.koltin.demo.bus

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.base.koltin.R
import com.base.koltin.bus.LiveDataBus
import com.base.koltin.extensions.start
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btn_main2.setOnClickListener {
            LiveDataBus
                .instance
                .with<String>("key_main1")
                .value = "Activity 1"
        }

        start_main1.setOnClickListener {
            finish()
//            start<Main1Activity>()
        }

        LiveDataBus
            .instance
            .with<String>("key_main2")
            .observe(this, {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
    }

    override fun onDestroy() {
        super.onDestroy()

        LiveDataBus
            .instance
            .remove("key_main2")
    }
}