package com.ian.app.helper.classes

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.ian.app.helper.interfaces.CommonHelperResult
import timber.log.Timber


/**
 * Created by Ian Damping on 01,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class CommonHelper: CommonHelperResult {

    override fun Context.myToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    override fun FragmentActivity.checkConnectivityStatus(status:(Boolean)->Unit) {
        LiveDataNetworkChangeListener(this).apply {
            this.observe(this@checkConnectivityStatus, Observer {
                status(it)
            })
        }
    }

    override fun Fragment.checkConnectivityStatus(status:(Boolean)->Unit) {
        if (context != null) {
            LiveDataNetworkChangeListener(context!!).apply {
                this.observe(this@checkConnectivityStatus.viewLifecycleOwner, Observer {
                    status(it)
                })
            }
        }
    }

    override fun timberLogE(msg: String?) {
        Timber.tag("#### timber logger ####").e(msg)
    }

    override fun timberLogD(msg: String?) {
        Timber.tag("#### timber logger ####").d(msg)
    }

    override fun timberLogI(msg: String?) {
        Timber.tag("#### timber logger ####").i(msg)
    }


}