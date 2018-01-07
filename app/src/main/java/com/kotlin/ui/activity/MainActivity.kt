package com.kotlin.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import com.kotlin.R
import com.kotlin.api.model.ItemModel
import com.kotlin.helper.Events
import com.kotlin.ui.adapter.ViewPagerAdapter
import com.kotlin.ui.fragment.SearchFragment
import com.kotlin.ui.fragment.LikeFragment
import com.kotlin.ui.model.FragmentModel
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {
    val likeResults = ArrayList<ItemModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listFragments = ArrayList<FragmentModel>()

        listFragments.add(FragmentModel("Search Page", SearchFragment.newInstance() as Fragment))
        listFragments.add(FragmentModel("Like Page", LikeFragment.newInstance() as Fragment))

        view_pager.adapter = ViewPagerAdapter(supportFragmentManager, listFragments)
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun addLikeData(item: ItemModel) {
        takeIf { likeResults.find { it == item } == null}?.run { likeResults.add(item) }
    }

    private fun removeLikeData(item: ItemModel) {
        takeIf { likeResults.find { it == item } != null}?.run { likeResults.remove(item) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun likeItemSelectEvent(e: Events.LikeItemSelect) {
        addLikeData(e.itemModel)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun unlikeItemSelectEvent(e: Events.UnlikeItemSelect) {
        removeLikeData(e.itemModel)
    }
}
