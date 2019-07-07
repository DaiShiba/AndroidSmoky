package com.yomogi.smoky

import android.view.View
import androidx.databinding.BindingAdapter

object CustomBindingAdapter {
    //xmlに定義する際のBindingAdapter

    //Staticメソッドで定義する
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view : View, show : Boolean) {
        view.visibility = if(show) View.VISIBLE else View.GONE
    }
}