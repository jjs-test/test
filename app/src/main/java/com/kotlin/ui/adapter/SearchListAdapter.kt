package com.kotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import com.kotlin.api.model.ItemModel
import com.kotlin.helper.Events
import com.kotlin.ui.holder.SearchListHolder
import org.greenrobot.eventbus.EventBus

class SearchListAdapter(private val items: ArrayList<ItemModel>,
                        private val likeItems: ArrayList<ItemModel>)
    : BaseListAdapter(items) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        super.onBindViewHolder(holder, position)
        (holder as SearchListHolder).setLikeData(isLikeData(items[position]))
        with((holder)) {
            holder.itemView.setOnClickListener({
                EventBus.getDefault().post(Events.LikeItemSelect(items.get(position)))
                setLikeData(true)
            })
        }
    }

    private fun isLikeData(item: ItemModel): Boolean {
        return likeItems.find { it == item } != null
    }
}