package com.equipo6.seqr.adapter

import com.equipo6.seqr.models.Tour

interface HistoryListener {
    fun onHistoryClicked(tour: Tour, position: Int)
}