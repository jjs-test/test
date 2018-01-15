package com.kotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import com.kotlin.api.model.ItemModel
import com.kotlin.helper.Events
import com.kotlin.ui.holder.SearchListHolder
import org.greenrobot.eventbus.EventBus

class LikeListAdapter(private val items: ArrayList<ItemModel>)
    : BaseListAdapter(items) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        super.onBindViewHolder(holder, position)
        holder?.itemView?.setOnClickListener({
            EventBus.getDefault().post(Events.UnlikeItemSelect(items.get(position)))
        })
    }
}