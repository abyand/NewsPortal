package com.myans.newsportal.data.entities

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("country_id")
    val countryId: String,
    val name: String,
    @SerializedName("flag_url")
    val flagUrl: String,
    var selected: Boolean = false
)