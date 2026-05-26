package com.rohan.moonlightmodelstudent.api

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.JsonObject
import com.rohan.moonlightmodelstudent.Login
import com.rohan.moonlightmodelstudent.R
import com.rohan.moonlightmodelstudent.ui.Dashboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException


class Services(val context: Context) {
//    var token = Tokenizer.getEncryptedShredPrefs(context).getString("token","").toString()
    fun logout(){
    val pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
    pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
    pDialog.titleText = "Loading"
    pDialog.setCancelable(false)
    pDialog.show()
    Tokenizer.getEncryptedShredPrefs(context).edit()
        .remove("token")
        .remove("islogged")
        .apply()
    pDialog.dismissWithAnimation()
    val i = Intent(context, Login::class.java)
    context.startActivity(i)
    (context as Activity).finish()
    }
    fun login(username:String,password:String) {
        val pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()
        val obj = JsonObject()
        val param = JsonObject()
        param.addProperty("username", username)
        param.addProperty("password", password)
        obj.addProperty("name", "loginstudent")
        obj.add("param", param)
        RetrofitInstance(context).apiInterface.login(obj).enqueue(object : Callback<ResponseModel?> {
            @SuppressLint("CommitPrefEdits")
            override fun onResponse(
                call: Call<ResponseModel?>,
                response: Response<ResponseModel?>
            ) {
                if(response.isSuccessful){
                    try {
                        val res = response.body()
                        if (res?.status == 200){
                            Tokenizer.getEncryptedShredPrefs(context).edit()
                                .putString("token",res.result.token)
                                .putBoolean("islogged",true).apply()
                            pDialog.dismissWithAnimation()
                            val i = Intent(context, Dashboard::class.java)
                            context.startActivity(i)
                            (context as Activity).finish()
                        }else{
                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                            pDialog.setTitleText("Login Failed!").contentText = res?.message
                        }
                    }catch (e: IOException){
                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                        pDialog.setTitleText("Login Failed!").contentText = "Something went wrong, please try again later!"
                    }
                }else{
                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                    pDialog.setTitleText("Login Failed!").contentText = "Something went wrong, please try again later!"
                }
            }

            override fun onFailure(call: Call<ResponseModel?>, t: Throwable) {
                when (t) {
                    is SocketTimeoutException -> {
                        pDialog.changeAlertType(SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        pDialog.setTitleText("No Internet Connection!")
                            .setContentText("Please check your internet connection and try again later!")
                            .setCustomImage(R.drawable.ic_no_internet)
                    }
                    is NoConnectivityException -> {
                        pDialog.changeAlertType(SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        pDialog.setTitleText("Failed to connect server!")
                            .setContentText("Failed to connect with server, please try again later!")
                            .setCustomImage(R.drawable.ic_no_internet)
                    }

                    else -> {
                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                        pDialog.setTitleText("Login Failed!").contentText = "Something went wrong, please try again later!"
                    }
                }
            }
        })
    }
}