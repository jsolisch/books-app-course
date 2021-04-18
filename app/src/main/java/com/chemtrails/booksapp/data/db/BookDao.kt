package com.chemtrails.booksapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chemtrails.booksapp.data.model.Book
import java.util.*

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(book: Book)

    @Query("SELECT MAX(modified) FROM books")
    suspend fun lastModified(): Long?

    @Query("SELECT * FROM books")
    fun loadBooks(): LiveData<List<Book>>
}