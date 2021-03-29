package com.chemtrails.booksapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chemtrails.booksapp.data.model.Book

@Dao
interface BookDao {

    @Insert
    suspend fun addBook(book: Book)

    @Query("SELECT * FROM books")
    fun loadBooks(): LiveData<List<Book>>
}