package com.equipo6.seqr.models

import com.google.firebase.firestore.DocumentReference
import java.io.Serializable
import java.util.*

class Tour : Serializable {
    lateinit var user: String
    lateinit var check_in: Date
    lateinit var checkpoint: String
}