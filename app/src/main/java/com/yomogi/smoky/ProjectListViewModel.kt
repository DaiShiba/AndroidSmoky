package com.yomogi.smoky

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

open class ProjectListViewModel(application: Application) : AndroidViewModel(application) {

    //監視対象のLiveData
    private val projectListObservable : LiveData<List<Project>> = ProjectRepository.getInstance().getProjectList("DaiShiba")

    private val LogTag = ProjectListViewModel::class.java.simpleName

    //LiveDataを公開するUIに向けて
    open fun getProjectListObservable() : LiveData<List<Project>> {
        Log.d(LogTag, "getProjectListObservable:$projectListObservable")
        return this.projectListObservable
    }
}