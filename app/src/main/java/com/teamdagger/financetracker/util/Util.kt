package com.synapsisid.smartdeskandroombooking.util

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

object Util {

    val listCategory = listOf<String>(
        "Meal",
        "Daily Needs",
        "Food",
        "Drink",
        "Shopping",
        "Subscription",
        "Transportation",
    )

    val simpleDate = SimpleDateFormat("yyyyMd")
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val sdfDate = SimpleDateFormat("yyyy-MM-dd")
    val sdfDateFullMonth = SimpleDateFormat("EEE, MMMM dd, yyyy")

    fun timestamp():String{
        var cal = Calendar.getInstance()
        var str = sdf.format(cal.time)
        return str
    }

    fun sqlToNormalDate(str:String):String{
        var sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
        val time = sdf.parse(str)

        return Util.sdf.format(time)
    }

    fun getName(context: Context):String{
        val sp = context.getSharedPreferences("mypref",Context.MODE_PRIVATE)
        val name = sp.getString("NAME","")
        return name!!
    }

    fun getUserID(context:Context):Int{
        val sp = context.getSharedPreferences("mypref",Context.MODE_PRIVATE)
        var id = sp.getInt("ID",0)
        return id
    }

    fun saveUserID(context:Context, id: Int){
        val sp = context.getSharedPreferences("mypref",Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt("ID", id)
        editor.apply()
    }

    fun showSnackError(view: View,msg:String){
        Snackbar.make(view,msg,Snackbar.LENGTH_LONG).show()
    }


}