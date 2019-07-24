package com.yomogi.smoky

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.yomogi.smoky.Firebase.AuthenticationFormFragment
import com.yomogi.smoky.Firebase.AuthenticationFragment
import kotlinx.android.synthetic.main.activity_firebase.*

@Suppress("PLUGIN_WARNING")
class FirebaseActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        val topText = TextView(this)
        topText.text = "Firebase検証用Activity"
        firebasePanel.addView(topText)

        Authentication.setOnClickListener{
            val inputFragment = AuthenticationFormFragment.newInstance()
            supportFragmentManager.beginTransaction().
                add(R.id.input_container, inputFragment, AuthenticationFragment.TAG).commit()

            val outputFragment = AuthenticationFragment.newInstance()
            supportFragmentManager.beginTransaction().
                add(R.id.output_container, outputFragment, AuthenticationFragment.TAG).commit()
        }
    }
}
