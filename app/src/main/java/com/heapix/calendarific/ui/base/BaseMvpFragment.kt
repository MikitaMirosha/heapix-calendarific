package com.heapix.calendarific.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

abstract class BaseMvpFragment : MvpFragment(),
    BaseMvpView {

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun onViewCreated(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onViewCreated(view)
    }

    override fun showMessage(resId: Int) {
        (activity as? BaseMvpActivity)?.showMessage(resId)
    }

    override fun showMessage(msg: String?) {
        (activity as? BaseMvpActivity)?.showMessage(msg)
    }

    override fun handleRestError(e: Throwable) {
        (activity as? BaseMvpActivity)?.handleRestError(e)
    }

}