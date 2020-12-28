package com.thousand.aidynnury.global.functional

import android.content.Context
import com.thousand.aidynnury.global.extension.networkInfo

class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected
}