package com.myans.newsportal.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.myans.newsportal.data.entities.News
import kotlinx.coroutines.Dispatchers
import com.myans.newsportal.utils.Resource.Status.*

suspend fun <T, A> performGetOperation(databaseQuery: () -> T,
                               networkCall: suspend () -> Resource<A>,
                               saveCallResult: suspend (T) -> Unit,
                               dataBridge: suspend (A) -> T): Resource<T> {
    val cache = databaseQuery.invoke()
    val response = networkCall.invoke()
    if (response.status == SUCCESS) {
        saveCallResult(dataBridge(response.data!!))
        return Resource.success(dataBridge(response.data))
    } else {
        return Resource.error(response.message!!, cache)
    }

}