package com.manikbora.mynewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.manikbora.mynewsapp.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    }
}