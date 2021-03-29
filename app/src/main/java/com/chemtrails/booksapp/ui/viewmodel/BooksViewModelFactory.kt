package com.chemtrails.booksapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chemtrails.booksapp.data.db.AppDatabase

class BooksViewModelFactory(private val appCtx: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BooksViewModel(AppDatabase.getInstance(appCtx)) as T
    }
}