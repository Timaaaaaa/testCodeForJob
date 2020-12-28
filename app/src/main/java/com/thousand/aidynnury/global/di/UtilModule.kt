package com.thousand.aidynnury.global.di


import com.thousand.aidynnury.global.functional.NetworkHandler
import com.thousand.aidynnury.global.system.AppSchedulers
import com.thousand.aidynnury.global.system.ResourceManager
import com.thousand.aidynnury.global.system.SchedulersProvider
import com.example.helperapp.global.utils.ErrorHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {
    single { AppSchedulers() as SchedulersProvider }
    single { ResourceManager(androidContext()) }
    single { ErrorHandler(get()) }
    single { NetworkHandler(androidContext()) }
}