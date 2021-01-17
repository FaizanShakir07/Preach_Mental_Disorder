package com.preach.mentaldisorder.Utils.Application


import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ConsumerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
//        AppCompatDelegate.MODE_NIGHT_YES
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@ConsumerApplication)
            modules(listOf(appModules, viewModelModules))
        }
    }

}