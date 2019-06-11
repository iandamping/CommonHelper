package com.ian.app.helper.util

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.ian.app.helper.R
import kotlinx.android.synthetic.main.custom_failed_dialog.view.*

/**
 *
Created by Ian Damping on 11/06/2019.
Github = https://github.com/iandamping
 */

inline fun<reified T : Activity> Context.alertHelperFailed(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        this@alertHelperFailed.startActivity<T>()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()

}

inline fun <reified T : Activity> Context.alertHelperSuccess(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        this@alertHelperSuccess.startActivity<T>()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()

}