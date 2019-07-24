package com.yomogi.smoky

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.yomogi.smoky.Firebase.FirestoreActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_trial_realm.*

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

        val moveFirebaseActivity = Button(this)
        moveFirebaseActivity.text = "Firebase検証画面に遷移"
        moveFirebaseActivity.setOnClickListener {
            startActivity(Intent(this, FirebaseActivity::class.java))
        }
        topLinear.addView(moveFirebaseActivity)

        addButton("Firestore検証画面に遷移",FirestoreActivity())

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

    //TOP Activityにボタンを追加
    private fun addButton(buttonText:String,activity: Activity){
        val button = Button(this)
        button.text = buttonText
        button.setOnClickListener{
            startActivity(Intent(this, activity::class.java))
        }
        topLinear.addView(button)
    }
}
