package com.chemtrails.booksapp.data.repository

import androidx.lifecycle.LiveData
import com.chemtrails.booksapp.api.BooksApi
import com.chemtrails.booksapp.data.db.AppDatabase
import com.chemtrails.booksapp.data.model.Book
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestFailedException(reason: String) : RuntimeException(reason)

@Module
@InstallIn(ActivityRetainedComponent::class)
class BookRepository @Inject constructor(
    private val db: AppDatabase,
    private val client: BooksApi
) {
    private val booksDao = db.bookDao();

    fun loadBooks(): LiveData<List<Book>> = booksDao.loadBooks()

    suspend fun refreshBooks() {
        val lastUpdate = booksDao.lastModified()
        val response = client.getBooks(lastUpdate)
        if (!response.isSuccessful) {
            throw RequestFailedException("Could not load books")
        }
        response.body()?.forEach { book ->
            booksDao.addBook(book)
        }
    }

    suspend fun addBook(book: Book) = withContext(Dispatchers.IO) {
        val response = client.addBook(book)
        if (!response.isSuccessful) {
            throw RequestFailedException("Could not add book.")
        }
        booksDao.addBook(book)
    }
}