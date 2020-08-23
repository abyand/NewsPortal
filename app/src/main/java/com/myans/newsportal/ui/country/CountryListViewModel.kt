package com.myans.newsportal.ui.country

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.myans.newsportal.data.entities.Country
import com.myans.newsportal.data.repository.CountryRepository

class CountryListViewModel @ViewModelInject constructor(
    private val countryRepository: CountryRepository
): ViewModel() {
    fun getCountryList(): List<Country> = countryRepository.getCountryListJson()
}