package com.myans.newsportal.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

fun <T> fromObjectToString(item: T?): String? {
    if (item == null) {
        return null
    }
    val gson = Gson()
    val type: Type = object : TypeToken<T?>() {}.type
    return gson.toJson(item, type)
}

fun <T> fromStringToObject(itemString: String?, type: Type): T? {
    if (itemString == null) {
        return null
    }
    val gson = Gson()
    return gson.fromJson<T>(itemString, type)
}