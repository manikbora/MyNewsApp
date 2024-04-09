package com.manikbora.mynewsapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.manikbora.mynewsapp.data.model.SavedArticle

@Dao
interface SavedArticleDao {

    @Insert
    suspend fun insertArticle(article: SavedArticle)

    @Delete
    suspend fun deleteArticle(article: SavedArticle)

    @Query("SELECT * FROM saved_articles")
    suspend fun getAllSavedArticles(): List<SavedArticle>

    @Query("SELECT * FROM saved_articles WHERE id = :articleId")
    suspend fun getArticleById(articleId: Long): SavedArticle?

}