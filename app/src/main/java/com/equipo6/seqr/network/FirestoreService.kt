package com.equipo6.seqr.network

import com.equipo6.seqr.models.Tour
import com.equipo6.seqr.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query

const val TOURS_COLLECTION_NAME = "tours"

class FirestoreService {
    val firebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    val firebaseAuth = FirebaseAuth.getInstance()
    init {
        firebaseFirestore.firestoreSettings = settings
    }

    fun getToursOfUser(callback: Callback<List<Tour>>) {
        firebaseFirestore.collection(TOURS_COLLECTION_NAME).orderBy("check_in", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val list = result.toObjects(Tour::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
    }

    fun getUser(user: String, callback: Callback<User>) {
        firebaseFirestore.collection("users").document(user).get()
            .addOnSuccessListener {result ->
                if(result.exists()) {
                    val user = result.toObject(User::class.java)
                    callback.onSuccess(user)
                }
            }
    }

}