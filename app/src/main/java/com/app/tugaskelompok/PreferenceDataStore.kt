package com.app.tugaskelompok

import android.content.Context
import android.content.SharedPreferences

class PreferenceDataStore(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun saveValue1(value: String) {
        sharedPreferences.edit().putString("USER_EMAIL", value).apply()
    }

    fun getValue1(): String? {
        return sharedPreferences.getString("USER_EMAIL", null)
    }

    fun saveValue2(value: String) {
        sharedPreferences.edit().putString("USER_UID", value).apply()
    }

    fun getValue2(): String? {
        return sharedPreferences.getString("USER_UID", null)
    }

    fun eraseValues() {
        sharedPreferences.edit().remove("USER_EMAIL").remove("USER_UID").apply()
    }
}