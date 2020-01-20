package com.yomogi.smoky.firebase.remote_config

import android.util.Log
import android.widget.Toast
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.yomogi.smoky.MainActivity
import com.yomogi.smoky.R

object ConfigHelper {
    val remoteConfig = FirebaseRemoteConfig.getInstance()
    private val configSettings = FirebaseRemoteConfigSettings.Builder()
        .setMinimumFetchIntervalInSeconds(3600)
        .build()
    val TAG = ConfigHelper::class.java.simpleName

    init {
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    fun activated(activity : MainActivity) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val updated = task.getResult()
                    Log.d(TAG, "Config params updated: $updated")
                    Toast.makeText(activity, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Fetch failed",
                        Toast.LENGTH_SHORT).show()
                }
//                displayWelcomeMessage()
            }
    }
}