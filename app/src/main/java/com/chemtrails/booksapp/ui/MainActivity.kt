package com.chemtrails.booksapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chemtrails.booksapp.R
import com.chemtrails.booksapp.databinding.ActivityMainBinding
import com.chemtrails.booksapp.ui.adapter.BooksAdapter
import com.chemtrails.booksapp.ui.viewmodel.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: BooksViewModel by viewModels()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = BooksAdapter(this, viewModel.books)
        binding.books.adapter = adapter

        // Note: This is important you should never use UI views in the ViewModel!
        viewModel.toast.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}