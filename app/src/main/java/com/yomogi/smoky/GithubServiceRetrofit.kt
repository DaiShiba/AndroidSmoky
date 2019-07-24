package com.yomogi.smoky

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


//RetrofitInterface(APIのリクエストを管理する)
const val HTTPS_API_GITHUB_URL : String = "https://api.github.com/"

interface GithubServiceRetrofit {
    //一覧の取得
    @GET("users/{user}/repos")
    //Call<List<Project>> getProjectList(@Path("user") String user) By Java
    fun getProjectList(@Path("user") user : String) : Call<List<Project>>

    //詳細の取得
    @GET("/repos/{user}/{reponame}")
    fun getProjectDetails(@Path("user") user : String,@Path("reponame") projectname : String) : Call<Project>


}