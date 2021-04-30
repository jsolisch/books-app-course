package com.chemtrails.booksapp.ui.viewmodel

import androidx.lifecycle.*
import com.chemtrails.booksapp.data.model.Book
import com.chemtrails.booksapp.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {
    val books: LiveData<List<Book>> = repository.loadBooks()
    val toast: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.refreshBooks()
            } catch (e: Exception) {
                viewModelScope.launch { toast.value = "Could not refresh books" }
            }
        }
    }
}