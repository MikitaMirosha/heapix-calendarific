package com.heapix.calendarific.ui.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat

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

    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    }

    fun showKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, 0)
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @Throws(Exception::class)
    fun createFragment(
        fragmentClass: Class<out BaseMvpFragment>,
        args: Bundle?,
        tagId: String
    ): BaseMvpFragment {
        val fragment = fragmentClass.newInstance()
        var bundle: Bundle? = args
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle.putString("name", fragmentClass.name + tagId)
        fragment.setHasOptionsMenu(true)
        fragment.arguments = args
        return fragment
    }

    fun getDisplayWidth(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        return displayMetrics.widthPixels
    }
}
