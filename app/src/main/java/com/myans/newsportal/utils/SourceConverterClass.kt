package com.myans.newsportal.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myans.newsportal.data.entities.Source
import java.lang.reflect.Type


class SourceConverterClass {
    @TypeConverter
    fun fromSourceObject(source: Source): String? {
        if (source == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Source?>() {}.type
        return gson.toJson(source, type)
    }

    @TypeConverter
    fun toCountryLangList(countryLangString: String?): Source? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Source?>() {}.type
        return gson.fromJson<Source>(countryLangString, type)
    }

}