package com.manikbora.mynewsapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.manikbora.mynewsapp.ui.fragments.NewsCategoryFragment

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 7 // Number of tabs

    override fun createFragment(position: Int): Fragment {
        // Create and return the fragment for the corresponding tab
        return when (position) {
            0 -> NewsCategoryFragment.newInstance("headlines")
            1 -> NewsCategoryFragment.newInstance("business")
            2 -> NewsCategoryFragment.newInstance("entertainment")
            3 -> NewsCategoryFragment.newInstance("sports")
            4 -> NewsCategoryFragment.newInstance("health")
            5 -> NewsCategoryFragment.newInstance("science")
            6 -> NewsCategoryFragment.newInstance("technology")
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
