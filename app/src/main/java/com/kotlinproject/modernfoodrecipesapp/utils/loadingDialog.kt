package com.kotlinproject.modernfoodrecipesapp.utils
import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.kotlinproject.modernfoodrecipesapp.R

class loadingDialog(private val activity: Activity) {
    private var alertDialog: AlertDialog? = null
    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_dialog_loading, null))
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog!!.show()
    }

    fun dismisDialog() {
        alertDialog!!.dismiss()
    }
}