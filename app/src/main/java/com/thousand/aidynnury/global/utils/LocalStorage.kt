package com.thousand.aidynnury.global.utils

import com.example.helperapp.global.utils.AppConstants
import com.pixplicity.easyprefs.library.Prefs

object LocalStorage {

    const val PREF_NO_VAL = "no_val"
    const val PREF_DEFAULT_LANGUAGE = "ru"

    private const val PREF_ACCESS_TOKEN = "access_token"
    private const val PREF_FIRST_TIME_LAUNCHED = "first_time_launched"
    private const val PREF_PUSH = "push_notification"
    private const val PREF_SOUND = "sound_notification"
    private const val PREF_LANGUAGE = "LANGUAGE"
    private const val PREF_NAME = "PREF_NAME"
    private const val PREF_PHONE= "PREF_PHONE"
    private const val PREF_EMAIL= "PREF_EMAIL"
    private const val PREF_EXECUTER_ID = "EXECUTER_ID"
    private const val PREF_AUTHOR= "PREF_AUTHOR"

    fun setAccessToken(accessToken: String) = Prefs.putString(PREF_ACCESS_TOKEN, accessToken)

    fun getAccessToken(): String = Prefs.getString(PREF_ACCESS_TOKEN, "tokasen")

    fun setAuthor(Author: String) = Prefs.putString(PREF_AUTHOR, Author)

    fun getAuthor(): String = Prefs.getString(PREF_AUTHOR, "Айнур Турсынбаева")

    fun setExecutor_ID(executer_id : Int) = Prefs.putInt(PREF_EXECUTER_ID, executer_id)

    fun getExecutor_ID(): Int= Prefs.getInt(PREF_EXECUTER_ID, 0)

    fun setLanguage(language: String) = Prefs.putString(PREF_LANGUAGE, language)

    fun getLanguage(): String = Prefs.getString(PREF_LANGUAGE, AppConstants.PREF_RUS)

    fun setName(name : String) = Prefs.putString(PREF_NAME, name)

    fun getName(): String = Prefs.getString(PREF_NAME, "1")

    fun setPhone(phone : String) = Prefs.putString(PREF_PHONE, phone)

    fun getPhone(): String = Prefs.getString(PREF_PHONE, "1")

    fun setMail(Mail : String) = Prefs.putString(PREF_EMAIL, Mail)

    fun getMail(): String = Prefs.getString(PREF_EMAIL, "1@mail.ru")

    fun setPush(push: Boolean) = Prefs.putBoolean(PREF_PUSH, push)

    fun getPush() : Boolean = Prefs.getBoolean(PREF_PUSH, true)

    fun setSound(sound: Boolean) = Prefs.putBoolean(PREF_SOUND, sound)

    fun getSound() : Boolean = Prefs.getBoolean(PREF_SOUND, true)

    fun getUpdate() : Int = Prefs.getInt("Update", 9)

    fun setFirstTimeLaunched(isFirstTime: Boolean) = Prefs.putBoolean(PREF_FIRST_TIME_LAUNCHED, isFirstTime)

    fun isFirstTimeLaunched(): Boolean = Prefs.getBoolean(PREF_FIRST_TIME_LAUNCHED, true)

    fun deleteStorageDATA() = Prefs.edit().clear().apply()
}