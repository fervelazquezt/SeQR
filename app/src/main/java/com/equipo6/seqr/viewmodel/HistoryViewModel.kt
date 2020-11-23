package com.equipo6.seqr.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.equipo6.seqr.models.Tour
import com.equipo6.seqr.network.Callback
import com.equipo6.seqr.network.FirestoreService
import java.lang.Exception

class HistoryViewModel : ViewModel() {
    private val db = FirestoreService()
    val listHistory: MutableLiveData<List<Tour>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        getHistoryFromFirebase()
    }

    fun getHistoryFromFirebase() {
        db.getToursOfUser(object: Callback<List<Tour>> {
            override fun onSuccess(result: List<Tour>?) {
                listHistory.postValue(result)
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                processFinished()
            }

        })
    }


    fun processFinished() {
        isLoading.value = true
    }

}