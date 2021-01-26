package com.ian.app.helper.classes

import android.util.TypedValue
import android.view.*
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.ian.app.helper.R
import com.ian.app.helper.interfaces.ViewHelperResult

internal class ViewHelper : ViewHelperResult {

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

    override fun setMargins(
        view: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val bottomMarginDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, bottom.toFloat(), view.context.resources.displayMetrics
            ).toInt()
            val topMarginDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, top.toFloat(), view.context.resources.displayMetrics
            ).toInt()
            val rightMarginDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, right.toFloat(), view.context.resources.displayMetrics
            ).toInt()
            val leftMarginDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, left.toFloat(), view.context.resources.displayMetrics
            ).toInt()

            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(leftMarginDp, topMarginDp, rightMarginDp, bottomMarginDp)
            view.requestLayout()
        }
    }
}