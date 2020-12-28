package com.thousand.aidynnury.global.di

import com.thousand.aidynnury.main.di.interactorRepositoryModule
import com.thousand.aidynnury.main.di.mainModule


val appModule = listOf(
                        networkModule,
                        utilModule,
                        mainModule,
                        interactorRepositoryModule
                        )