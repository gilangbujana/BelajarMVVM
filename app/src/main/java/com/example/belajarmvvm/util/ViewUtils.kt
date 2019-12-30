package com.example.belajarmvvm.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import org.joda.time.Interval
import java.util.*


fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun ProgressBar.hide(){
    visibility = View.INVISIBLE
}

fun View.snackbar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("OKE"){
            snackbar.dismiss()
        }
    }.show()
}

fun getDiffHours(startDate: Date, endDate: Date): Int{

    var interval = Interval(startDate.getTime(), endDate.getTime());
    var period = interval.toPeriod();
    return period.getHours();
}