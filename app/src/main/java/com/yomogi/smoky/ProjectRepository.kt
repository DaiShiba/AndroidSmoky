package com.yomogi.smoky

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.annotation.Nullable
import retrofit2.Call
import retrofit2.Response


open class ProjectRepository {

    private var githubServiceRetrofit : GithubServiceRetrofit
    private val TAG = ProjectRepository::class.java.simpleName

    //コンストラクタ Retrofitインスタンスの生成
    private constructor() {
        val retrofit : Retrofit = Retrofit.Builder().baseUrl(HTTPS_API_GITHUB_URL)
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
    open fun getProjectList(userId : String) : LiveData<List<Project>> {
        Log.d(TAG,"getProjectList Called")
        Log.d(TAG,"userId:$userId")

        val data : MutableLiveData<List<Project>> = MutableLiveData()

        //Retrofitで非同期リクエスト->CallbackでProject型ListのMutableLiveDataにセット
        githubServiceRetrofit.getProjectList(userId).enqueue(object : Callback<List<Project>> {

            //レスポンス返却(成功時)
            override fun onResponse(call: Call<List<Project>>, @Nullable response: Response<List<Project>>) {
                Log.d(TAG,"onResponse Called")

                //レスポンスからListを取得し、詳細を順次出力
                val list = response.body()
                Log.d(TAG,"ListSize:${requireNotNull(list).size}")
                list.forEach { Log.d(TAG,"PJ名 : ${it.name}")}
                data.postValue(list)
            }

            //レスポンス返却失敗時
            override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                Log.d(TAG,"onFailure Called")
                data.postValue(null)
            }
        })

        return data
    }

    //詳細取得APIにリクエストしレスポンスをLiveDataとする
    open fun getProjectDetail(userId: String, projectName : String) : LiveData<Project> {
        val data : MutableLiveData<Project> = MutableLiveData()
        Log.d(TAG,"getProjectDetail called")
        Log.d(TAG,"userId : $userId")
        Log.d(TAG,"projectName : $projectName")

        //Retrofitで非同期リクエスト->CallbackでProject型ListのMutableLiveDataにセット
        githubServiceRetrofit.getProjectDetails(userId, projectName).enqueue(object : Callback<Project> {

            override fun onResponse(call: Call<Project>, @Nullable response: Response<Project>) {
                Log.d(TAG,"onResponse called")

                val list = response.body()
                Log.d(TAG,"PJ名:${requireNotNull(list).name}")
                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                data.postValue(list)
            }

            override fun onFailure(call: Call<Project>, t: Throwable) {
                // TODO;Error
                data.postValue(null)
            }
        })

        return data
    }


}

