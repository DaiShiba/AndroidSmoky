package com.yomogi.smoky

import android.app.Application
import androidx.annotation.NonNull
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel



open class ProjectViewModel : AndroidViewModel {

    private val projectObservable : LiveData<ProjectRealm>
    private val projectId : String

    //Viewとバインドするためのデータクラス
    open var project : ObservableField<ProjectRealm> = ObservableField()

    //コンストラクタで継承しているAndroidViewModelにアプリケーションをセット＆ProjectIDをセット
    constructor(application: Application, projectId : String) : super(application){
        this.projectId = projectId
        projectObservable = ProjectRepository.getInstance().getProjectDetail("", projectId)
    }

    //リポジトリのレスポンスを取得
    open fun getObservableProject() : LiveData<ProjectRealm> {
        return projectObservable
    }

    //プロジェクトのセット
    open fun setProject(project : ProjectRealm) {
        this.project.set(project)
    }
}

open class Factory : ViewModelProvider.NewInstanceFactory {
    @NonNull private val application : Application
    private val projectId : String

    constructor(@NonNull application: Application, projectId: String) {
        this.application = application
        this.projectId = projectId
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return ProjectViewModel(application, projectId) as T
    }
}