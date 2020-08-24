package com.myans.newsportal.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myans.newsportal.data.entities.Country
import com.myans.newsportal.utils.fromStringToObject
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import javax.inject.Inject

val SELECTED_COUNTRY = "SELECTED_COUNTRY"
class PreferenceProvider @Inject constructor(
    @ApplicationContext
    private val appContext: Context,
    private val gson: Gson
) {
    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveSelectedCountry(country: Country) {
        preference.edit().putString(
            SELECTED_COUNTRY,
            gson.toJson(country)
        ).apply()
    }

    fun getSelectedCountry(): Country {
        val selectedCountryString = preference.getString(SELECTED_COUNTRY, null)
        selectedCountryString?.let {
            val type: Type = object : TypeToken<Country?>() {}.type
            return fromStringToObject(selectedCountryString, type) ?: getDefaultSelectedCountry()
        }
        return getDefaultSelectedCountry()
    }

    private fun getDefaultSelectedCountry(): Country {
        return Country(
            countryId = "id",
            name = "Indonesia",
            flagUrl = "https://www.countryflags.io/id/flat/64.png"
        )
    }
}