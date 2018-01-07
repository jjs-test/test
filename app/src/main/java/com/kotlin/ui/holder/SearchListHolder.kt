package com.kotlin.ui.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kotlin.api.model.ItemModel
import com.kotlin.helper.Settable
import kotlinx.android.synthetic.main.holder_list.view.*

class SearchListHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView), Settable<ItemModel> {
    private val imageView: ImageView? = itemView.iv_holder_list
    private val textView: TextView? = itemView.tv_holder_list_title
    private val likeView: View? = itemView.tv_holder_list_like

    init {
    }

    override fun setData(t: ItemModel?) {
        if (t == null) {
            return
        }
        Glide.with(itemView.context)
                .load(t.avatarUrl)
                .dontAnimate()
                .into(imageView)
        textView?.setText(t.login)
    }

    fun setLikeData(isLike: Boolean) {
        likeView?.visibility = takeIf { isLike }?.run { View.VISIBLE } ?: View.GONE
    }
}