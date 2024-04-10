package com.manikbora.mynewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.manikbora.mynewsapp.data.api.RetrofitClient
import com.manikbora.mynewsapp.data.database.NewsDatabase
import com.manikbora.mynewsapp.data.repository.NewsRepository
import com.manikbora.mynewsapp.databinding.FragmentBusinessBinding
import com.manikbora.mynewsapp.ui.adapters.NewsAdapter
import com.manikbora.mynewsapp.ui.viewmodels.BusinessViewModel

class BusinessFragment : Fragment(), NewsAdapter.OnArticleClickListener {

    private lateinit var businessViewModel: BusinessViewModel
    private lateinit var businessNewsAdapter: NewsAdapter
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        val view = binding.root

        businessNewsAdapter = NewsAdapter(this)

        val recyclerView = binding.rvBusiness
        recyclerView.adapter = businessNewsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create instance of NewsApiService
        val newsApiService = RetrofitClient.createService()

        val newsDatabase = NewsDatabase.getDatabase(requireContext()) // Obtain an instance of NewsDatabase
        val savedArticleDao = newsDatabase.savedArticleDao() // Get the savedArticleDao from NewsDatabase
        val newsRepository = NewsRepository(newsApiService, savedArticleDao)


        businessViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(BusinessViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return BusinessViewModel(newsRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(BusinessViewModel::class.java)

        businessViewModel.businessNews.observe(viewLifecycleOwner, Observer { articles ->
            businessNewsAdapter.submitList(articles)
        })

        // Fetch business news data
        businessViewModel.fetchBusinessNews()

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
