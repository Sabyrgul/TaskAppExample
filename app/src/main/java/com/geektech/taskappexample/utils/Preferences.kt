package com.geektech.taskappexample.utils

import android.content.Context
import android.net.Uri

class Preferences(private val context: Context) {

    companion object {
        private const val HAVE_SEEN_ONBOARDING_KEY = "have_seen_on_boarding"
        private const val PICTURE_INSERTED_KEY = "picture_inserted"
        private const val NAME_INSERTED_KEY = "name_inserted"
    }

    private val prefs = context.getSharedPreferences(
        "utils",
        Context.MODE_PRIVATE
    )

    fun setPictureInserted(pictureUri: Uri) {
        prefs.edit().putString(PICTURE_INSERTED_KEY, pictureUri.toString()).apply()
    }

    fun getPictureInserted():Uri? {
        val uriString=prefs.getString(PICTURE_INSERTED_KEY,null)
        return if(uriString!=null) Uri.parse(uriString)
        else null
    }


    fun setNameInserted(text: String) {
        prefs.edit().putString(NAME_INSERTED_KEY, text).apply()
    }

    fun getNameInserted() = prefs.getString(NAME_INSERTED_KEY, "")


    fun setHaveSeenOnBoarding(boolean: Boolean) {
        prefs.edit().putBoolean(HAVE_SEEN_ONBOARDING_KEY, boolean).apply()
    }

    fun getHaveSeenBoarding() = prefs.getBoolean(HAVE_SEEN_ONBOARDING_KEY, false)
}