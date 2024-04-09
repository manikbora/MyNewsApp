package com.manikbora.mynewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.manikbora.mynewsapp.data.api.RetrofitClient
import com.manikbora.mynewsapp.data.repository.NewsRepository
import com.manikbora.mynewsapp.databinding.FragmentHeadlinesBinding
import com.manikbora.mynewsapp.ui.adapters.HeadlinesAdapter
import com.manikbora.mynewsapp.ui.viewmodels.HeadlinesViewModel

class HeadlinesFragment : Fragment(), HeadlinesAdapter.OnArticleClickListener {

    private lateinit var headlinesViewModel: HeadlinesViewModel
    private lateinit var headlinesAdapter: HeadlinesAdapter

    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        val view = binding.root

        headlinesAdapter = HeadlinesAdapter(this)

        val recyclerView = binding.rvHeadlines
        recyclerView.adapter = headlinesAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create instance of NewsApiService
        val newsApiService = RetrofitClient.createService()

        // Create NewsRepository instance with NewsApiService
        val newsRepository = NewsRepository(newsApiService)

        // Initialize HeadlinesViewModel with NewsRepository
        headlinesViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HeadlinesViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return HeadlinesViewModel(newsRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(HeadlinesViewModel::class.java)


        headlinesViewModel.topHeadlines.observe(viewLifecycleOwner, Observer { articles ->
            headlinesAdapter.submitList(articles)
        })

        // Fetch top headlines when the fragment is created
        headlinesViewModel.fetchTopHeadlines("in")

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