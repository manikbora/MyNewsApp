package com.manikbora.mynewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.manikbora.mynewsapp.R
import com.manikbora.mynewsapp.data.api.RetrofitClient
import com.manikbora.mynewsapp.data.repository.NewsRepository
import com.manikbora.mynewsapp.databinding.FragmentBusinessBinding
import com.manikbora.mynewsapp.databinding.FragmentHealthBinding
import com.manikbora.mynewsapp.ui.adapters.NewsAdapter
import com.manikbora.mynewsapp.ui.viewmodels.BusinessViewModel
import com.manikbora.mynewsapp.ui.viewmodels.HealthViewModel

class HealthFragment : Fragment(), NewsAdapter.OnArticleClickListener {

    private lateinit var healthViewModel: HealthViewModel
    private lateinit var healthNewsAdapter: NewsAdapter
    private var _binding: FragmentHealthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthBinding.inflate(inflater, container, false)
        val view = binding.root

        healthNewsAdapter = NewsAdapter(this)

        val recyclerView = binding.rvHealth
        recyclerView.adapter = healthNewsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create instance of NewsApiService
        val newsApiService = RetrofitClient.createService()

        // Create NewsRepository instance with NewsApiService
        val newsRepository = NewsRepository(newsApiService)

        healthViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HealthViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return HealthViewModel(newsRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(HealthViewModel::class.java)

        healthViewModel.healthNews.observe(viewLifecycleOwner, Observer { articles ->
            healthNewsAdapter.submitList(articles)
        })

        // Fetch health news data
        healthViewModel.fetchHealthNews()

        return view
    }

    override fun onArticleClicked(articleUrl: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(articleUrl)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
