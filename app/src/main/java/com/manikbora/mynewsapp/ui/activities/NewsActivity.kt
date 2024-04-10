package com.manikbora.mynewsapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.manikbora.mynewsapp.R
import com.manikbora.mynewsapp.databinding.ActivityNewsBinding
import com.manikbora.mynewsapp.ui.fragments.HomeFragment

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.popBackStack()
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.searchNewsFragment -> {
                    //navController.popBackStack()
                    navController.navigate(R.id.searchNewsFragment)
                    true
                }
                else -> false
            }
        }
    }
}
