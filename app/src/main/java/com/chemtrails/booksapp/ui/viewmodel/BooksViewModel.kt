package com.chemtrails.booksapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chemtrails.booksapp.data.Book

class BooksViewModel : ViewModel() {
    private val _books = mutableListOf<Book>() // We will remove this later.
    val books: MutableLiveData<List<Book>> = MutableLiveData(emptyList())
    var title: MutableLiveData<String> = MutableLiveData("")
    var author: MutableLiveData<String> = MutableLiveData("")
    val toast: MutableLiveData<String> = MutableLiveData()

    fun addBook() {
        if (title.value.isNullOrBlank() || author.value.isNullOrBlank()) {
            toast.value = "Please fill in title and author!"
            return
        }
        _books.add(Book(title.value ?: "", author.value ?: ""))
        books.value = _books
        title.value = ""
        author.value = ""
    }
}