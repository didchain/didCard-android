package com.nbs.didcard

import com.nbs.android.lib.base.BaseApplication

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class DidCardApp:BaseApplication() {
    companion object{
        lateinit var instance : DidCardApp
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}