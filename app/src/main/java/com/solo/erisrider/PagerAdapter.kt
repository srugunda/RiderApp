package com.solo.erisrider

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(private val myContext: Context, fragmentManager: FragmentManager, var numOfTabs : Int) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                return PendingJobsFragment()
            }
            1 -> {
                return AcceptedJobsFragment()
            }
            else -> {
                return getItem(position)
            }
        }
    }

    override fun getCount(): Int {
        return numOfTabs
    }
}