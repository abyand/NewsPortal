package com.myans.newsportal.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myans.newsportal.R
import com.myans.newsportal.data.entities.Country
import com.myans.newsportal.data.local.PreferenceProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class CountryRepository @Inject constructor(@ApplicationContext private val context: Context,
                                            private val gson: Gson,
                                            private val preferenceProvider: PreferenceProvider
) {
    fun getCountryListJson(): List<Country> {
        val inputStream = context.resources.openRawResource(R.raw.country_list)
        val countryListString = readTextFile(inputStream)
        val sType = object : TypeToken<List<Country>>() { }.type
        return gson.fromJson<List<Country>>(countryListString, sType)
    }

    fun saveSelectedCountry(country: Country) {
        preferenceProvider.saveSelectedCountry(country)
    }
    fun getSelectedCountry(): Country = preferenceProvider.getSelectedCountry()

    fun readTextFile(inputStream: InputStream): String? {
        val outputStream = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var len: Int = 0
        try {
            while (inputStream.read(buf).also({ len = it }) != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
        }
        return outputStream.toString()
    }
}