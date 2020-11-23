package com.equipo6.seqr.network

import java.lang.Exception

interface Callback<T> {
    fun onSuccess(result: T?)
    fun onFailed(exception: Exception)
}