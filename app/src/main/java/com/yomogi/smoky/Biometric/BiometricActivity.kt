package com.yomogi.smoky.Biometric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yomogi.smoky.Biometric.ui.main.BiometricFragment
import com.yomogi.smoky.R

class BiometricActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.biometric_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BiometricFragment.newInstance())
                .commitNow()
        }
    }
}