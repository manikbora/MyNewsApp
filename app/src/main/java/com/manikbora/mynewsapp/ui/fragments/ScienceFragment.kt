package com.manikbora.mynewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.manikbora.mynewsapp.data.api.RetrofitClient
import com.manikbora.mynewsapp.data.repository.NewsRepository
import com.manikbora.mynewsapp.databinding.FragmentScienceBinding
import com.manikbora.mynewsapp.ui.adapters.NewsAdapter
import com.manikbora.mynewsapp.ui.viewmodels.ScienceViewModel

class ScienceFragment : Fragment(), NewsAdapter.OnArticleClickListener {

    private lateinit var scienceViewModel: ScienceViewModel
    private lateinit var scienceNewsAdapter: NewsAdapter
    private var _binding: FragmentScienceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScienceBinding.inflate(inflater, container, false)
        val view = binding.root

        scienceNewsAdapter = NewsAdapter(this)

        val recyclerView = binding.rvScience
        recyclerView.adapter = scienceNewsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create instance of NewsApiService
        val newsApiService = RetrofitClient.createService()

        val newsRepository = NewsRepository(newsApiService)

        scienceViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ScienceViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ScienceViewModel(newsRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        })[ScienceViewModel::class.java]

        scienceViewModel.scienceNews.observe(viewLifecycleOwner) { articles ->
            scienceNewsAdapter.submitList(articles)
        }

        // Fetch science news data
        scienceViewModel.fetchScienceNews()

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
