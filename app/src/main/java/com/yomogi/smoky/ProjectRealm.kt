package com.yomogi.smoky

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class ProjectRealm(
    @PrimaryKey open var id : Long = 0,
    @Required open var name : String = "",
    @Required open var full_name : String = "",
    //open var owner : User = User(),
    open var html_url : String = "",
    open var description : String  = "",
    open var url : String = "",
    open var created_at : Date = Date(),
    open var updated_at : Date = Date(),
    open var pushed_at : Date = Date(),
    open var git_url : String = "",
    open var ssh_url : String = "",
    open var clone_url : String = "",
    open var svn_url : String = "",
    open var homepage : String = "",
    open var stargazers_count : Int = 0,
    open var watchers_count : Int = 0,
    open var language : String = "",
    open var has_issues : Boolean = false,
    open var has_downloads : Boolean = false,
    open var has_wiki : Boolean = false,
    open var has_pages : Boolean = false,
    open var forks_count : Int = 0,
    open var open_issuses_count  : Int = 0,
    open var watchers : Int = 0,
    open var default_branch : String = ""
) : RealmObject() {}