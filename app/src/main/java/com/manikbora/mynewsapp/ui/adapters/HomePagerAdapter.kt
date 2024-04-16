package com.manikbora.mynewsapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.manikbora.mynewsapp.ui.fragments.NewsCategoryFragment

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 7 // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return NewsCategoryFragment.newInstance()
    }
}
