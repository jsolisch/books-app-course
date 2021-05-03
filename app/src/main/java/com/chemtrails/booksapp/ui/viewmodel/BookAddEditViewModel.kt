package com.chemtrails.booksapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chemtrails.booksapp.data.model.Book
import com.chemtrails.booksapp.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BookAddEditViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    sealed class Status {
        data class Success(val message: String) : Status()
        data class Error(val message: String) : Status()
        data class Cancel(val message: String) : Status()
    }

    var id: String? = null
    var isbn: String? = null

    var title: MutableLiveData<String> = MutableLiveData("")
    var author: MutableLiveData<String> = MutableLiveData("")
    val status: MutableLiveData<Status> = MutableLiveData()

    fun addBook() {
        if (title.value.isNullOrBlank() || author.value.isNullOrBlank()) {
            status.value = Status.Error("Please fill in title and author!")
            return
        }
        val book = Book(
            id = id ?: UUID.randomUUID().toString(),
            title = title.value ?: "",
            author = author.value ?: "",
            isbn = isbn ?: ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addBook(book)
                viewModelScope.launch {
                    title.value = ""
                    author.value = ""
                    status.value = Status.Success("Successfully added book!")
                }
            } catch (e: Exception) {
                viewModelScope.launch { status.value = Status.Error("Could not save book!") }
            }
        }

    }

    fun cancel() {
        status.value = Status.Cancel("")
    }
}