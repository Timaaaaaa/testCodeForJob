package com.thousand.aidynnury.main.di

import com.thousand.aidynnury.main.interactor.UserInteractor
import com.thousand.aidynnury.main.repository.UserRepository
import com.thousand.aidynnury.main.repository.UserRepositoryImpl
import org.koin.dsl.module

val interactorRepositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single { UserInteractor(get(), get()) }
}