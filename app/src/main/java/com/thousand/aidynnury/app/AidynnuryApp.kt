package com.thousand.aidynnury.app

import android.app.Application
import android.content.ContextWrapper
import android.util.Log
import com.pixplicity.easyprefs.library.Prefs
import com.thousand.aidynnury.global.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.net.URISyntaxException

class AidynnuryApp: Application() {

    companion object {

        private lateinit var aidynnury: AidynnuryApp

    }

    override fun onCreate() {
        Log.i("AidynnuryApp","onCreate")
        super.onCreate()

        aidynnury = this

        startKoin{
            androidContext(this@AidynnuryApp)
            modules(appModule)
        }

        initPrefs()


    }

    private fun initPrefs() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }


}