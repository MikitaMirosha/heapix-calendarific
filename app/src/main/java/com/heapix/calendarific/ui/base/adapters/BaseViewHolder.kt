package com.heapix.calendarific.ui.base.adapters

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.Disposable
import kotlinx.android.extensions.LayoutContainer

/**
 * Base class for all view holders to be used in concrete implementations of [BaseListAdapter]
 */
/**
 * Base class for all view holders to be used in concrete implementations of [BaseListAdapter]
 */
abstract class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    abstract fun bind(model: T)

    override val containerView: View?
        get() = itemView

    val context: Context
        get() = itemView.context

    fun setOnClickListener(block: () -> Unit) {
        itemView.setOnClickListener {
            block()
        }
    }

    fun addDisposable(disposable: Disposable) {}

    /**
     * Override this method to cancel any behaviour you don't want to continue after the view has been recycled
     * This is most likely async behaviour
     */
    open fun recycle() {}

    fun getColor(@ColorRes color: Int) = ContextCompat.getColor(itemView.context, color)
}