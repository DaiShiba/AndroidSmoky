package com.yomogi.smoky.firebase.remote_config.real_time_database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DatabaseHelper {
    fun callDb() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.reference.child("location")

        // データの更新
//        myRef.setValue("Hello World ! user")

        // データの追加
        myRef.push().setValue("Hello World ! user add!!")
    }
}