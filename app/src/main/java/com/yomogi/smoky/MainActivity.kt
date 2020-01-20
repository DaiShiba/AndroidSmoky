package com.yomogi.smoky

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.yomogi.smoky.Biometric.BiometricActivity
import com.yomogi.smoky.firebase.remote_config.ConfigHelper
import com.yomogi.smoky.firebase.remote_config.fire_store.FireStoreHelper
import com.yomogi.smoky.firebase.remote_config.real_time_database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if(!task.isSuccessful) {
                    Log.d(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                //Instance ID Tokenの取得
                val token = task.result?.token

                //LogとToastに出力
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG,msg)
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            })

        initButton()
    }

    private fun initButton() {
        val crashButton = Button(this)
        crashButton.text = "Crash!"
        crashButton.setOnClickListener {
            Crashlytics.getInstance().crash() // Force a crash
            Crashlytics.log("TestLog")
        }
        //KotlinAndroidExtensionでレイアウトで定義しているIDを変数として使用する
        topLinear.addView(crashButton)

        val moveRealmButton = Button(this)
        moveRealmButton.text = "Realm確認画面に遷移"
        moveRealmButton.setOnClickListener {
            val intent = Intent(this, TrialRealmActivity::class.java)
            startActivity(intent)
        }
        topLinear.addView(moveRealmButton)

        val moveGithubActivity = Button(this)
        moveGithubActivity.text = "GitHub確認画面に遷移"
        moveGithubActivity.setOnClickListener {
            startActivity( Intent(this, GithubActivity::class.java))
        }
        topLinear.addView(moveGithubActivity)

        val remoteConfigHelperCalled = Button(this)
        remoteConfigHelperCalled.text = "remoteConfig"
        remoteConfigHelperCalled.setOnClickListener {
            ConfigHelper.activated(this)
        }
        topLinear.addView(remoteConfigHelperCalled)

        val remoteConfigHelperDisplay = Button(this)
        remoteConfigHelperDisplay.text = "remoteConfig_display"
        remoteConfigHelperDisplay.setOnClickListener {
            Toast.makeText(this, ConfigHelper.remoteConfig.getString("location_acquired_frequency"), Toast.LENGTH_LONG).show()
        }
        topLinear.addView(remoteConfigHelperDisplay)

        val callRealTimeDBHelper = Button(this)
        callRealTimeDBHelper.text = "callRealTimeDBHelper"
        callRealTimeDBHelper.setOnClickListener {
            val realTimeDatabaseHelper = DatabaseHelper()
            realTimeDatabaseHelper.callDb()
        }
        topLinear.addView(callRealTimeDBHelper)

        val callFireStore = Button(this)
        callFireStore.text = "callFireStore"
        callFireStore.setOnClickListener {
            val fireStoreHelper = FireStoreHelper()
            fireStoreHelper.callFireStoreSet()
        }
        topLinear.addView(callFireStore)

        val callBiometric = Button(this)
        callBiometric.text = "callBiometric"
        callBiometric.setOnClickListener {
            startActivity(Intent(this, BiometricActivity::class.java))
        }
        topLinear.addView(callBiometric)

    }
}
