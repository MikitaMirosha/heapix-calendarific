package com.heapix.calendarific.ui.base

import android.os.Bundle
import android.widget.Toast

abstract class BaseMvpActivity : MvpActivity(),
    BaseMvpView {

    abstract fun getLayoutId(): Int

    open fun onPreCreate() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onPreCreate()
        setContentView(getLayoutId())

        onCreateActivity(savedInstanceState)
    }

    abstract fun onCreateActivity(savedInstanceState: Bundle?)

    override fun showMessage(resId: Int) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun handleRestError(e: Throwable) {
        showMessage("error during api call")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
