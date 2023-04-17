package com.bankirobot.palkinto.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SaveUserData(context: Context) {

    companion object {
        private const val PREF_FIRST_NAME = "firstName"
        private const val PREF_LAST_NAME = "lastName"
        private const val PREF_EMAIL = "email"
        private const val PREF_FIRST_LAUNCH = "firstLaunch"
        private const val PREF_USER_ID = "userID"
    }

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    var firstLaunch = sharedPreferences.getBoolean(PREF_FIRST_LAUNCH, true)
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(PREF_FIRST_LAUNCH, value)
            editor.apply()
        }

    var userID = sharedPreferences.getString(PREF_USER_ID, "")!!
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putString(PREF_USER_ID, value)
            editor.apply()
        }

    var firstName = sharedPreferences.getString(PREF_FIRST_NAME, "")!!
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putString(PREF_FIRST_NAME, value)
            editor.apply()
        }

    var lastName = sharedPreferences.getString(PREF_LAST_NAME, "")!!
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putString(PREF_LAST_NAME, value)
            editor.apply()
        }

    var email = sharedPreferences.getString(PREF_EMAIL, "")!!
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putString(PREF_EMAIL, value)
            editor.apply()
        }

    fun clearUser() {
        val editor = sharedPreferences.edit()
        editor.putString(PREF_USER_ID, "")
        editor.apply()
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}