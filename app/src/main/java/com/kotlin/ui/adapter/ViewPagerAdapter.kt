package com.kotlin.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kotlin.ui.model.FragmentModel

class ViewPagerAdapter(fragmentManager: FragmentManager,
                       private val listFragments: List<FragmentModel>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return listFragments[position].fragment
    }

    override fun getCount(): Int {
        return listFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return listFragments[position].title
    }


}