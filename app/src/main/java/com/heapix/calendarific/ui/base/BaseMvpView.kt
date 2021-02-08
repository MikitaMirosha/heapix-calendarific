package com.heapix.calendarific.ui.base

import com.arellomobile.mvp.MvpView

interface BaseMvpView : MvpView {
    fun showMessage(resId: Int)
    fun showMessage(msg: String?)
    fun handleRestError(e: Throwable)

//    @StateStrategyType(SkipStrategy::class)
//    fun requestPermissions(vararg permissions: String, listener: OnPermissionsListener)

//    fun showProductCantBeReadyDueToTime()
}