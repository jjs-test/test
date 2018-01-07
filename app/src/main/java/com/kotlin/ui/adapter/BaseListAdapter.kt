package com.kotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kotlin.R
import com.kotlin.api.model.ItemModel
import com.kotlin.helper.Events
import com.kotlin.ui.holder.SearchListHolder
import org.greenrobot.eventbus.EventBus

open class BaseListAdapter(private val items: ArrayList<ItemModel>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return SearchListHolder(LayoutInflater.from(parent?.context).inflate(R.layout.holder_list, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as SearchListHolder).setData(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}