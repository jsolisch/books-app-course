package com.chemtrails.booksapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chemtrails.booksapp.data.db.AppDatabase
import com.chemtrails.booksapp.data.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val db: AppDatabase) : ViewModel() {
    private val booksDao = db.bookDao();
    val books = booksDao.loadBooks()

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
            booksDao.addBook(book)
        }
        title.value = ""
        author.value = ""
    }
}