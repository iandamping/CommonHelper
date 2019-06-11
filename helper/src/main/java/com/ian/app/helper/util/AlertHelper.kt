package com.ian.app.helper.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.ian.app.helper.R
import kotlinx.android.synthetic.main.custom_failed_dialog.view.*

/**
 *
Created by Ian Damping on 11/06/2019.
Github = https://github.com/iandamping
 */

inline fun<reified T : Activity> Context.alertHelperFailed(tittle: String?,noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.init()
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        startActivity(intent)
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()

}

inline fun <reified T : Activity> Context.alertHelperSuccess(tittle: String?,noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.init()
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        startActivity(intent)
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()

}