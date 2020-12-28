package com.example.helperapp.global.utils

import com.thousand.aidynnury.global.system.ResourceManager


class ErrorHandler(private val resourceManager: ResourceManager) {

    fun proceed(error: Throwable, messageListener: (String) -> Unit = {}) {
        //messageListener(error.errorMessage(resourceManager))
    }
}