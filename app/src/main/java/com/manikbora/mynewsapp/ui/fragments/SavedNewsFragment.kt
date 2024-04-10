package com.manikbora.mynewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.manikbora.mynewsapp.data.model.SavedArticle
import com.manikbora.mynewsapp.databinding.FragmentSavedNewsBinding
import com.manikbora.mynewsapp.ui.adapters.SavedArticleAdapter
import com.manikbora.mynewsapp.ui.viewmodels.ArticleViewModel

class SavedNewsFragment : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var savedArticleAdapter: SavedArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and adapter
        savedArticleAdapter = SavedArticleAdapter()
        binding.rvSavedNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = savedArticleAdapter
        }

        // Initialize ViewModel
        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)

        // Observe the list of saved articles
        articleViewModel.getAllSavedArticles().observe(viewLifecycleOwner, Observer { savedArticles ->
            savedArticleAdapter.submitList(savedArticles)
        })
    }
}
