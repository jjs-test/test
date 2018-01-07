package com.kotlin.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.R
import com.kotlin.api.model.ItemModel
import com.kotlin.helper.Events
import com.kotlin.ui.activity.MainActivity
import com.kotlin.ui.adapter.LikeListAdapter
import kotlinx.android.synthetic.main.fragment_like.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LikeFragment : Fragment() {
    private var likeAdapter: LikeListAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance() : LikeFragment? {
            val fragment = LikeFragment().apply {
                arguments = Bundle()
            }

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_like, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        view?.run{
            likeAdapter = LikeListAdapter((context as MainActivity).likeResults)
            with(rv_fragment_like){
                adapter = likeAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                itemAnimator = DefaultItemAnimator()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun likeItemSelectEvent(e: Events.LikeItemSelect) {
        likeAdapter?.notifyDataSetChanged()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun unlikeItemSelectEvent(e: Events.UnlikeItemSelect) {
        likeAdapter?.notifyDataSetChanged()
    }
}