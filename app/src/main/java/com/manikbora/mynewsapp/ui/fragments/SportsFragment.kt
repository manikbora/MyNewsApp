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
import com.manikbora.mynewsapp.data.api.RetrofitClient
import com.manikbora.mynewsapp.data.repository.NewsRepository
import com.manikbora.mynewsapp.databinding.FragmentSportsBinding
import com.manikbora.mynewsapp.ui.adapters.NewsAdapter
import com.manikbora.mynewsapp.ui.viewmodels.SportsViewModel

class SportsFragment : Fragment(), NewsAdapter.OnArticleClickListener {

    private lateinit var sportsViewModel: SportsViewModel
    private lateinit var sportsNewsAdapter: NewsAdapter
    private var _binding: FragmentSportsBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSportsBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        sportsNewsAdapter = NewsAdapter(this)

        val recyclerView = binding.rvSports
        recyclerView.adapter = sportsNewsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create instance of NewsApiService
        val newsApiService = RetrofitClient.createService()

        val newsRepository = NewsRepository(newsApiService)

        sportsViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SportsViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return SportsViewModel(newsRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(SportsViewModel::class.java)

        sportsViewModel.sportsNews.observe(viewLifecycleOwner, Observer { articles ->
            sportsNewsAdapter.submitList(articles)
        })

        // Fetch sports news data
        sportsViewModel.fetchSportsNews()

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