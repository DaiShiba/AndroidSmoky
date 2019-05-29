package com.yomogi.smoky

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class Item(
    @PrimaryKey open var id : String = UUID.randomUUID().toString(),
    @Required open var name : String = "",
    open var kind : String = "",
    open var weight : Int = 0
) : RealmObject() {}