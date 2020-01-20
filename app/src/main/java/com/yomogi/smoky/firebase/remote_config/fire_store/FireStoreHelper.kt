package com.yomogi.smoky.firebase.remote_config.fire_store

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreHelper {
    val TAG = FireStoreHelper::class.java.simpleName

    fun callFireStoreSet() {
        val db = FirebaseFirestore.getInstance()
        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Daiki",
            "last" to "Shibata",
            "born" to 1995
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}