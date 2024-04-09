package com.manikbora.mynewsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_articles")
data class SavedArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val sourceId: String,
    val sourceName: String,
    val title: String,
    val url: String,
    val urlToImage: String
)