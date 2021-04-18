package com.chemtrails.booksapp.ui.viewmodel

import androidx.lifecycle.*
import com.chemtrails.booksapp.data.model.Book
import com.chemtrails.booksapp.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {
    val books: LiveData<List<Book>> = repository.loadBooks()
    var title: MutableLiveData<String> = MutableLiveData("")
    var author: MutableLiveData<String> = MutableLiveData("")
    val toast: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                repository.refreshBooks()
            } catch (e: Exception) {
                toast.value = "Could not refresh books"
            }
        }
    }

    fun addBook() {
        if (title.value.isNullOrBlank() || author.value.isNullOrBlank()) {
            toast.value = "Please fill in title and author!"
            return
        }
        val book = Book(title = title.value ?: "", author = author.value ?: "")
        viewModelScope.launch {
            try {
                repository.addBook(book)
            } catch (e: Exception) {
                toast.value = "Could not save book!"
            }
        }
        title.value = ""
        author.value = ""
    }
}