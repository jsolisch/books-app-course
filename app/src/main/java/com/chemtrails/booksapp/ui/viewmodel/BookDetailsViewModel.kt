package com.chemtrails.booksapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chemtrails.booksapp.data.model.Book
import com.chemtrails.booksapp.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    sealed class Status() {
        object Processing : Status()
        object Deleted : Status()
        data class Error(val message: String) : Status()
    }

    val status: MutableLiveData<Status> = MutableLiveData()

    fun removeBook(book: Book) {
        status.value = Status.Processing
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteBook(book)
                viewModelScope.launch {
                    status.value = Status.Deleted
                }
            } catch (e: Exception) {
                viewModelScope.launch {
                    status.value = Status.Error("Could not delete book!")
                }
            }
        }
    }
}