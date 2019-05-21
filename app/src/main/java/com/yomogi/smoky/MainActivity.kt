package com.yomogi.smoky

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val crashButton = Button(this)
        crashButton.text = "Crash!"
        crashButton.setOnClickListener {
            //Crashlytics.getInstance().crash() // Force a crash
            Crashlytics.log("TestLog")
        }
        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))

        val moveRealmButton = Button(this)
        moveRealmButton.text = "Realm確認画面に遷移"
        moveRealmButton.setOnClickListener {
            val intent = Intent(this, TrialRealmActivity::class.java)
            startActivity(intent)
        }
        addContentView(moveRealmButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))

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
    }
}
