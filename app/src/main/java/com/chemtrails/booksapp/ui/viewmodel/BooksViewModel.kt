package com.chemtrails.booksapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chemtrails.booksapp.api.BooksApi
import com.chemtrails.booksapp.data.db.AppDatabase
import com.chemtrails.booksapp.data.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val db: AppDatabase,
    private val client: BooksApi
) : ViewModel() {
    private val booksDao = db.bookDao();
    val books = booksDao.loadBooks()

    init {
        viewModelScope.launch {
            val response = try {
                client.getBooks()
            } catch (e: Exception) {
                return@launch
            }
            if (!response.isSuccessful) return@launch
            response.body()?.forEach { book ->
                booksDao.addBook(book)
            }
        }
    }

    var title: MutableLiveData<String> = MutableLiveData("")
    var author: MutableLiveData<String> = MutableLiveData("")
    val toast: MutableLiveData<String> = MutableLiveData()

    fun addBook() {
        if (title.value.isNullOrBlank() || author.value.isNullOrBlank()) {
            toast.value = "Please fill in title and author!"
            return
        }
        val book = Book(title = title.value ?: "", author = author.value ?: "")
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                client.addBook(book)
            } catch (e: Exception) {
                Log.e("BookModel", "Request failed", e)
                return@launch
            }
            if (response.isSuccessful) {
                booksDao.addBook(book)
            }
        }
        title.value = ""
        author.value = ""
    }
}