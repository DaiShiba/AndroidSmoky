package com.yomogi.smoky

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.R.attr.data
import androidx.annotation.Nullable
import retrofit2.Call
import retrofit2.Response


open class ProjectRepository {

    var githubServiceRetrofit : GithubServiceRetrofit

    //コンストラクタ Retorofitインスタンスの生成
    private constructor() {
        var retrofit : Retrofit = Retrofit.Builder().baseUrl(GithubServiceRetrofit.HTTPS_API_GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        githubServiceRetrofit  = retrofit.create(GithubServiceRetrofit::class.java)
    }

    companion object {
        //Repository static
        private var projectRepository = ProjectRepository()

        fun getInstance(): ProjectRepository {
            return projectRepository
        }
    }

    //一覧取得APIにリクエストしレスポンスをLiveDataとする
    open fun getProjectList(userId : String) : LiveData<List<ProjectRealm>> {
        val data : MutableLiveData<List<ProjectRealm>> = MutableLiveData()

        //Retrofitで非同期リクエスト->CallbackでProjectRealm型ListのMutableLiveDataにセット
        githubServiceRetrofit.getProjectList(userId).enqueue(object : Callback<List<ProjectRealm>> {

            //レスポンス返却(成功時)
            override fun onResponse(call: Call<List<ProjectRealm>>, @Nullable response: Response<List<ProjectRealm>>) {
                data.postValue(response.body())
            }

            //レスポンス返却失敗時
            override fun onFailure(call: Call<List<ProjectRealm>>, t: Throwable) {
                data.postValue(null)
            }
        })

        return data
    }

    //詳細取得APIにリクエストしレスポンスをLiveDataとする
    open fun getProjectDetail(userId: String, projectName : String) : LiveData<ProjectRealm> {
        val data : MutableLiveData<ProjectRealm> = MutableLiveData()

        //Retrofitで非同期リクエスト->CallbackでProjectRealm型ListのMutableLiveDataにセット
        githubServiceRetrofit.getProjectDetails(userId, projectName).enqueue(object : Callback<ProjectRealm> {

            override fun onResponse(call: Call<ProjectRealm>, @Nullable response: Response<ProjectRealm>) {
                // TODO;遅延のシミュレート？
                //simulateDelay()
                data.postValue(response.body())
            }

            override fun onFailure(call: Call<ProjectRealm>, t: Throwable) {
                // TODO;Error
                data.postValue(null)
            }
        })

        return data
    }


}

