package com.heapix.calendarific.ui.world

import android.view.View
import com.heapix.calendarific.R
import com.heapix.calendarific.ui.base.BaseMvpFragment

class WorldFragment : BaseMvpFragment(), WorldView {

    override fun getLayoutId(): Int = R.layout.fragment_world

    override fun onViewCreated(view: View) {

    }

    companion object {
        fun newInstance(): WorldFragment = WorldFragment()
    }

}