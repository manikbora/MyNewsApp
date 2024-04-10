package com.manikbora.mynewsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.manikbora.mynewsapp.data.model.SavedArticle
import com.manikbora.mynewsapp.databinding.ItemNewsBinding

class SavedArticleAdapter :
    ListAdapter<SavedArticle, SavedArticleAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class ViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: SavedArticle) {
            binding.apply {
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvSource.text = article.sourceName
                tvDateTime.text = article.publishedAt

                Glide.with(root)
                    .load(article.urlToImage)
                    .into(ivArticleImage)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<SavedArticle>() {
        override fun areItemsTheSame(oldItem: SavedArticle, newItem: SavedArticle): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SavedArticle, newItem: SavedArticle): Boolean {
            return oldItem == newItem
        }
    }
}
