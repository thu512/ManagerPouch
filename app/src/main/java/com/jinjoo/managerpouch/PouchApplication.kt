package com.jinjoo.managerpouch

import android.app.Application
import android.content.Context

class PouchApplication : Application() {

    init{
        instance = this
    }

    companion object {
        lateinit var instance: PouchApplication
        fun getApplicationContext() : Context {
            return instance.applicationContext
        }
    }

}