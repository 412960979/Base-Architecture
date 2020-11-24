package com.base.koltin.extensions

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import org.junit.Test

class ContextExtensionKtTest : TestCase(){
    private val TAG = "ContextExtensionKtTest"

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testGetScreenWidthAndHeight(){
        Log.i(TAG, "ScreenWidth: ${appContext.screenWidth}  ScreenHeight: ${appContext.screenHeight}")
    }

    @Test
    fun testDpToPxInt(){
        Log.i(TAG, "dp to px to int: ${appContext.dpToPxInt(18f)}")
    }
}