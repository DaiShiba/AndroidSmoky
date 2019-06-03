package com.yomogi.smoky

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

open class ProjectListViewModel : AndroidViewModel {

    //監視対象のLiveData
    private var projectListObservable : LiveData<List<ProjectRealm>> = ProjectRepository.getInstance().getProjectList("")

    //コンストラクタ
    constructor(application: Application) : super(application)

    //LiveDataを公開する
    open fun getProjectListObservable() : LiveData<List<ProjectRealm>> {
        return this.projectListObservable
    }
}