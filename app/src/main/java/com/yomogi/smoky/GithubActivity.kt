package com.yomogi.smoky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class GithubActivity : AppCompatActivity() {
    val LOG = GithubActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(LOG,"onCreate Called")

        setContentView(R.layout.activity_github)

        //Fragmentの履歴を持っていない場合は、新規取得
        if (savedInstanceState == null) {
            Log.d(LOG,"supportFragmentManager.beginTransaction Called")
            val fragment = ProjectListFragment()

            supportFragmentManager.beginTransaction().
                add(R.id.fragment_container, fragment, ProjectListFragment.TAG).commit()
        }
    }

    //詳細画面に遷移
    fun show(project : Project) {
        Log.d(LOG,"show Called")
        val projectFragment : ProjectFragment = ProjectFragment.forProject(project.name)

        supportFragmentManager.beginTransaction().addToBackStack("project")
            .replace(R.id.fragment_container, projectFragment, null).commit()
    }
}
