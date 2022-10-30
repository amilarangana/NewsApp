package com.amila.newsapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View


object DialogHelper {

    fun showErrorDialog(activity: Activity, title:String, message: String){
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("ok"
            ) { _, _ ->  }.show()
    }
}