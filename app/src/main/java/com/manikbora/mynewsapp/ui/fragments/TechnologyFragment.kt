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
import com.manikbora.mynewsapp.databinding.FragmentTechnologyBinding
import com.manikbora.mynewsapp.ui.adapters.NewsAdapter
import com.manikbora.mynewsapp.ui.viewmodels.BusinessViewModel
import com.manikbora.mynewsapp.ui.viewmodels.TechnologyViewModel

class TechnologyFragment : Fragment(), NewsAdapter.OnArticleClickListener {

    private lateinit var technologyViewModel: TechnologyViewModel
    private lateinit var technologyNewsAdapter: NewsAdapter
    private var _binding: FragmentTechnologyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTechnologyBinding.inflate(inflater, container, false)
        val view = binding.root

        technologyNewsAdapter = NewsAdapter(this)

        val recyclerView = binding.rvTechnology
        recyclerView.adapter = technologyNewsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create instance of NewsApiService
        val newsApiService = RetrofitClient.createService()

        // Create NewsRepository instance with NewsApiService
        val newsRepository = NewsRepository(newsApiService)

        technologyViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TechnologyViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return TechnologyViewModel(newsRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(TechnologyViewModel::class.java)

        technologyViewModel.technologyNews.observe(viewLifecycleOwner, Observer { articles ->
            technologyNewsAdapter.submitList(articles)
        })

        // Fetch technology news data
        technologyViewModel.fetchTechnologyNews()

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