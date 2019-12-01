package com.ian.app.helper.classes

import android.view.*
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.ian.app.helper.R
import com.ian.app.helper.interfaces.ViewHelperResult

internal class ViewHelper: ViewHelperResult {
    
    override fun FragmentActivity.fullScreenAnimation() {
        this.overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun ViewGroup.inflates(layout: Int): View {
        return LayoutInflater.from(context).inflate(layout, this, false)
    }

    override fun View.visible() {
        this.visibility = View.VISIBLE
    }

    override fun View.gone() {
        this.visibility = View.GONE
    }

    override fun EditText.requestError(message: String?) {
        if (this.text.isNullOrEmpty()) {
            this.requestFocus()
            this.error = message
        }

    }
}