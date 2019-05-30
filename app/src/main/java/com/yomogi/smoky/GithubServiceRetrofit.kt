package com.yomogi.smoky

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubServiceRetrofit {

    companion object {
        //RetrofitInterface(APIのリクエストを管理する)
        val HTTPS_API_GITHUB_URL : String = "https://api.github.com/"
    }

    //一覧の取得
    @GET("users/{users}/repos")
    //Call<List<ProjectRealm>> getProjectList(@Path("user") String user) By Java
    fun getProjectList(@Path("user") user : String) : Call<List<ProjectRealm>>

    //詳細の取得
    @GET("/repos/{user}/{reponame}")
    fun getProjectDetails(@Path("user") user : String,@Path("reponame") projectname : String) : Call<ProjectRealm>


}