package com.geektech.taskappexample.utils

import android.content.Context

class Preferences(private val context: Context) {

    companion object {
        private const val HAVE_SEEN_ONBOARDING_KEY = "have_seen_on_boarding"
        private const val PICTURE_INSERTED_KEY = "have_seen_on_boarding"
        private const val NAME_INSERTED_KEY = "have_seen_on_boarding"
    }

    private val prefs = context.getSharedPreferences(
        "utils",
        Context.MODE_PRIVATE
    )

    fun setPictureInserted(pictureUri: String) {
        prefs.edit().putString(PICTURE_INSERTED_KEY, pictureUri).apply()
    }

    fun getPictureInserted() = prefs.getString(PICTURE_INSERTED_KEY, "1")


    fun setNameInserted(text: String) {
        prefs.edit().putString(NAME_INSERTED_KEY, text).apply()
    }

    fun getNameInserted() = prefs.getString(NAME_INSERTED_KEY, "1")

    fun setHaveSeenOnBoarding() {
        prefs.edit().putBoolean(HAVE_SEEN_ONBOARDING_KEY, true).apply()
    }

    fun getHaveSeenBoarding() = prefs.getBoolean(HAVE_SEEN_ONBOARDING_KEY, false)
}