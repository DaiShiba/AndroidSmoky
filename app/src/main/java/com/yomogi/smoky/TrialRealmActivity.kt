package com.yomogi.smoky

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_trial_realm.*
import kotlinx.android.synthetic.main.content_trial_realm.*
import java.util.*

class TrialRealmActivity : AppCompatActivity() {

    val TAG = TrialRealmActivity::class.java.simpleName
    //lateinitでNonNullなプロパティーの初期化をconstructorより後に遅らせる
    lateinit var mRealm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trial_realm)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
        mRealm = Realm.getDefaultInstance()

        //Button配置レイアウトの取得
        val view = content_button

        //Insertボタンの追加
        //val insertButton = Button(this)
        val insertButton = findViewById(R.id.button1) as Button
        insertButton.text = "Insert"
        insertButton.setOnClickListener {
            insertRealm("chicken", "mune", 100)
        }
//        view.addView(insertButton)

        //FindAll
        //val findButton = Button(this)
        val findButton = findViewById(R.id.button2) as Button
        findButton.text = "Select"
        findButton.setOnClickListener {
            findAll()
        }
//        view.addView(findButton)
    }

    private fun findAll() {
        Log.d(TAG, "findALL Called")
        val getData = mRealm.where(Item::class.java).findAll()
        getData.forEach {
            Log.d(TAG, it.id)
            Log.d(TAG, it.name)
            Log.d(TAG, it.kind)
            Log.d(TAG, it.weight.toString())
        }
    }

    private fun insertRealm(name: String, kind: String, weight: Int) {
        Log.d(TAG, "insertRealm Called")
        mRealm.executeTransaction {
            var item = mRealm.createObject(Item::class.java, UUID.randomUUID().toString())
            item.name = name
            item.kind = kind
            item.weight = weight
            mRealm.copyToRealm(item)
        }
    }
}
