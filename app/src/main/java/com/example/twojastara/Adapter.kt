package com.example.twojastara

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

internal class Adapter(
    var context: Context,
    fm: FragmentManager,
    private var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                fragment1()
            }

            1 -> {
                fragment2()
            }

            2 -> {
                fragment3()
            }

            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}