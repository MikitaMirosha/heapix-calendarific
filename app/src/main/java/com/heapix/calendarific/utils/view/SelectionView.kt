package com.heapix.calendarific.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.heapix.calendarific.R
import kotlinx.android.synthetic.main.view_selection.view.*

class SelectionView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var isSelectionView: Boolean = false

    private val view: View =
        LayoutInflater.from(context).inflate(R.layout.view_selection, this, false)

    init {
        addView(view)
        context.theme.obtainStyledAttributes(attrs, R.styleable.SelectionView, 0, 0).apply {
            try {
                isSelectionView = getBoolean(R.styleable.SelectionView_isSelectionView, false)
                updateSelectionView()
            } finally {
                recycle()
            }
        }
    }

    private fun updateSelectionView() {
        if (isSelectionView) {
            vLlSelectionView.show()
        } else {
            vLlSelectionView.hide()
        }
    }
}