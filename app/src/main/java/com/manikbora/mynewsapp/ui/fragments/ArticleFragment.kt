package com.manikbora.mynewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.manikbora.mynewsapp.R
import com.manikbora.mynewsapp.data.database.NewsDatabase
import com.manikbora.mynewsapp.data.model.SavedArticle
import com.manikbora.mynewsapp.databinding.FragmentArticleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleUrl = arguments?.getString("url")

        // Load full news article in WebView
        binding.webView.apply {
            webViewClient = WebViewClient()
            articleUrl?.let {
                loadUrl(it)
            }
        }

        binding.fabFavorite.setOnClickListener {
            // Create a SavedArticle object with relevant data
            val savedArticle = SavedArticle(
                author = "Author", // Replace with actual author name if available
                content = "Content", // Replace with actual content if available
                description = "Description", // Replace with actual description if available
                publishedAt = "Published date", // Replace with actual published date if available
                sourceId = "Source ID", // Replace with actual source ID if available
                sourceName = "Source Name", // Replace with actual source name if available
                title = "Title", // Replace with actual title if available
                url = articleUrl ?: "", // URL of the article
                urlToImage = "URL to image" // Replace with actual URL to image if available
            )

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    // Get reference to the Room database
                    val newsDatabase = NewsDatabase.getDatabase(requireContext())

                    // Access the SavedArticleDao
                    val savedArticleDao = newsDatabase.savedArticleDao()

                    // Insert the saved article to Room database
                    savedArticleDao.insertArticle(savedArticle)
                }

                // Show a toast or perform any other UI action to indicate that the article is saved
                Toast.makeText(requireContext(), "Article saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}