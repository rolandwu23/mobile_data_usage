package com.grok.akm.sphtest.di

import com.grok.akm.sphtest.View.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ApiModule::class])
@Singleton
interface AppComponent {

    fun doInjection(mainActivity: MainActivity)

}