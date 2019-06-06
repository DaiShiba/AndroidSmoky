package com.yomogi.smoky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GithubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github)

        //Fragmentの履歴を持っていない場合は、新規取得
        if (savedInstanceState == null) {
            var fragment = ProjectListFragment()

            supportFragmentManager.beginTransaction().
                add(R.id.fragment_container, fragment, ProjectListFragment.TAG).commit()
        }
    }

    //詳細画面に遷移
    open fun show(project : ProjectRealm) {
        var projectFragment : ProjectFragment = ProjectFragment.forProject(project.name)

        supportFragmentManager.beginTransaction().addToBackStack("project")
            .replace(R.id.fragment_container, projectFragment, null).commit()
    }
}
