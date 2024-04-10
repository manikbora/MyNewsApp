package com.manikbora.mynewsapp.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.manikbora.mynewsapp.R
import com.manikbora.mynewsapp.databinding.FragmentHomeBinding
import com.manikbora.mynewsapp.ui.adapters.HomePagerAdapter


@Suppress("DEPRECATION")
class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var homePagerAdapter: HomePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        homePagerAdapter = HomePagerAdapter(this)
        viewPager.adapter = homePagerAdapter

        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Headlines"
                1 -> "Business"
                2 -> "Entertainment"
                3 -> "Sports"
                4 -> "Health"
                5 -> "Science"
                6 -> "Technology"
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // Load the corresponding fragment when the page changes
                when (position) {
                    0 -> loadFragment(HeadlinesFragment())
                    1 -> loadFragment(BusinessFragment())
                    2 -> loadFragment(EntertainmentFragment())
                    3 -> loadFragment(SportsFragment())
                    4 -> loadFragment(HealthFragment())
                    5 -> loadFragment(ScienceFragment())
                    6 -> loadFragment(TechnologyFragment())
                    else -> throw IllegalArgumentException("Invalid position: $position")
                }
            }
        })
    }

    private fun loadFragment(fragment: Fragment) {
        // Show the progress bar before loading the fragment
        binding.progressBar.visibility = View.VISIBLE

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()

        // Hide the progress bar after the fragment is loaded
        Handler().postDelayed({
            binding.progressBar.visibility = View.GONE
        }, 1000) // Delay for 1 second (adjust as needed)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
