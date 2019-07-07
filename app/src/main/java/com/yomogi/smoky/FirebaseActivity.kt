package com.yomogi.smoky

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_firebase.*

class FirebaseActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        val topText = TextView(this)
        topText.text = "Firebase検証用Activity"
        firebasePanel.addView(topText)

        val AuthenticationButton = Button(this)
        AuthenticationButton.text = "認証確認"
        firebasePanel.addView(AuthenticationButton)
    }
}
