package com.grok.akm.sphtest.di

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    var context: Context? = null
    var appComponent:AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        context = this
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this))
            .apiModule(ApiModule(this))
            .build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}