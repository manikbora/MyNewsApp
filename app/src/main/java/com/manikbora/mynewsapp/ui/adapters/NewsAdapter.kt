package com.manikbora.mynewsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.manikbora.mynewsapp.R
import com.manikbora.mynewsapp.data.model.Article
import com.manikbora.mynewsapp.databinding.ItemNewsBinding

class NewsAdapter(private val listener: OnArticleClickListener) :
    ListAdapter<Article, NewsAdapter.ViewHolder>(DiffCallback()) {

    interface OnArticleClickListener {
        fun onArticleClicked(articleUrl: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        holder.itemView.setOnClickListener {
            listener.onArticleClicked(article.url)
        }
    }

    class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.tvSource.text = article.source.name
            binding.tvDateTime.text = article.publishedAt

            // Load image using Glide
            Glide.with(binding.root)
                .load(article.urlToImage)
                .placeholder(R.drawable.placeholder) // Set placeholder image
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivArticleImage)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}
