package com.chs.lib_core.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * toast 工具类
 */
fun Context.toast(str:String,duration:Int){
    Toast.makeText(this,str,duration).show()
}

fun Context.toast(@StringRes resId:Int,duration:Int){
    Toast.makeText(this,getString(resId),duration).show()
}

fun Context.showShort(str:String){
    toast(str,Toast.LENGTH_SHORT)
}

fun Context.showShort(@StringRes resId:Int){
    toast(getString(resId),Toast.LENGTH_SHORT)
}

fun Context.showLong(str:String){
    toast(str,Toast.LENGTH_LONG)
}
fun Context.showLong(@StringRes resId:Int){
    toast(getString(resId),Toast.LENGTH_LONG)
}