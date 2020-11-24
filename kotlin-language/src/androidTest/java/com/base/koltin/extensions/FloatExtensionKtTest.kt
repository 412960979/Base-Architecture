package com.base.koltin.extensions

import android.util.Log
import junit.framework.TestCase
import org.junit.Test

class FloatExtensionKtTest : TestCase(){
    private val TAG = "FloatExtensionKtTest"

    @Test
    fun testDpToPx(){
        Log.i(TAG, 18f.dp.toString())
    }
}