package com.base.koltin.demo.bus

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.base.koltin.R
import com.base.koltin.bus.LiveDataBus
import com.base.koltin.extensions.start
import kotlinx.android.synthetic.main.activity_main1.*

class Main1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        btn_main1.setOnClickListener {
            LiveDataBus
                .instance
                .with<String>("key_main2")
                .value = "Activity 2"
        }

        start_main2.setOnClickListener {
            start<Main2Activity>()
//            finish()
        }

        LiveDataBus
            .instance
            .with<String>("key_main1")
            .observe(this, {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
    }

    override fun onDestroy() {
        super.onDestroy()

        LiveDataBus
            .instance
            .remove("key_main1")
    }
}