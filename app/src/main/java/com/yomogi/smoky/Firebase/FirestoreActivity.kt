package com.yomogi.smoky.Firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.yomogi.smoky.R
import kotlinx.android.synthetic.main.activity_firestore.*

class FirestoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore)

        val TAG = String.format(getString(R.string.log_tag), FirestoreActivity::class.java.simpleName)

        val db = FirebaseFirestore.getInstance()
        Toast.makeText(this, db.toString(), Toast.LENGTH_LONG).show()

        //尾崎豊の登録
        FireStoreInsertButton.setOnClickListener {
            // Create a new user with a first and last name
            val user = HashMap<String, Any>()
            user["first"] = "Ozaki"
            user["last"] = "Yutaka"
            user["born"] = 1965
            user["other"] = "僕が僕であるために"

            // Add a new document with a generated ID
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "DocumentSnapshot added with ID: ${documentReference.id}",
                        Toast.LENGTH_LONG).show()
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error adding document",Toast.LENGTH_LONG).show()
                    Log.w(TAG, "Error adding document", e)
                }
        }

        //Usersテーブルのデータ表示
        FireStoreReadButton.setOnClickListener {
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
    }
}
