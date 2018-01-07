package com.kotlin.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.R
import com.kotlin.api.OnApiResponse
import com.kotlin.api.model.ItemModel
import com.kotlin.api.model.SearchModel
import com.kotlin.api.service.ApiService
import com.kotlin.helper.Events
import com.kotlin.ui.activity.MainActivity
import com.kotlin.ui.adapter.SearchListAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class SearchFragment : Fragment() {
    private var call: Call? = null
    private val results = ArrayList<ItemModel>()
    private var searchAdapter: SearchListAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance() : SearchFragment? {
            val fragment = SearchFragment().apply {
                arguments = Bundle()
            }

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        view?.run{
            et_fragment_search.addTextChangedListener(
                    object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            call?.takeIf { it.isCanceled }?.cancel()
                            searchText(s.toString())
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        }
                    }
            )
            searchAdapter = SearchListAdapter(results, (context as MainActivity).likeResults)
            with(rv_fragment_search){
                adapter = searchAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                itemAnimator = DefaultItemAnimator()
            }
        }
    }

    private fun searchText(text: String) {
        call = ApiService.instance.startService(
                text,
                object : OnApiResponse<SearchModel> {
                    override fun onSuccess(code: Int, t: SearchModel) {
                        results.clear()
                        results.addAll(t.items)
                        rv_fragment_search.post({
                            searchAdapter?.run { notifyDataSetChanged() }
                        })
                    }

                    override fun onFail(code: Int, e: Exception) {
                    }
                }
        )
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
        searchAdapter?.notifyDataSetChanged()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun unlikeItemSelectEvent(e: Events.UnlikeItemSelect) {
        searchAdapter?.notifyDataSetChanged()
    }
}