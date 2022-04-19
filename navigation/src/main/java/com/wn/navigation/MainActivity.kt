package com.wn.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.flow.flow
import java.util.*
import androidx.navigation.NavOptions




class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var i = 0
    private lateinit var options: NavOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
        options = builder.build()

        jump()
    }

    private fun jump(){
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (i == 0){
                    val action = AFragmentDirections.actionAFragmentToBFragment()
                    navController.navigate(action, options)
                } else if (i == 1){
                    val action = BFragmentDirections.actionBFragmentToAFragment(1)
                    navController.navigate(action, options)
                } else if (i == 2){
                    navController.navigate(R.id.CFragment)
                } else if (i == 3){
                    val action = CFragmentDirections.actionCFragmentToAFragment(0)
                    navController.navigate(action, options)
                } else {
                    cancel()
                }

                i++
                Log.i("wn", "i=$i")
            }
        }, 2000, 2000)
    }
}