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
import com.manikbora.mynewsapp.data.database.NewsDatabase
import com.manikbora.mynewsapp.data.repository.NewsRepository
import com.manikbora.mynewsapp.databinding.FragmentEntertainmentBinding
import com.manikbora.mynewsapp.ui.adapters.NewsAdapter
import com.manikbora.mynewsapp.ui.viewmodels.BusinessViewModel
import com.manikbora.mynewsapp.ui.viewmodels.EntertainmentViewModel

class EntertainmentFragment : Fragment(), NewsAdapter.OnArticleClickListener {

    private lateinit var entertainmentViewModel: EntertainmentViewModel
    private lateinit var entertainmentNewsAdapter: NewsAdapter
    private var _binding: FragmentEntertainmentBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntertainmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        entertainmentNewsAdapter = NewsAdapter(this)

        val recyclerView = binding.rvEntertainment
        recyclerView.adapter = entertainmentNewsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create instance of NewsApiService
        val newsApiService = RetrofitClient.createService()

        val newsDatabase = NewsDatabase.getDatabase(requireContext()) // Obtain an instance of NewsDatabase
        val savedArticleDao = newsDatabase.savedArticleDao() // Get the savedArticleDao from NewsDatabase
        val newsRepository = NewsRepository(newsApiService, savedArticleDao)

        entertainmentViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(EntertainmentViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return EntertainmentViewModel(newsRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(EntertainmentViewModel::class.java)

        entertainmentViewModel.entertainmentNews.observe(viewLifecycleOwner, Observer { articles ->
            entertainmentNewsAdapter.submitList(articles)
        })

        // Fetch entertainment news data
        entertainmentViewModel.fetchEntertainmentNews()

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