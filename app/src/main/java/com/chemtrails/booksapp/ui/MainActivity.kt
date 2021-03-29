package com.chemtrails.booksapp.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chemtrails.booksapp.R
import com.chemtrails.booksapp.data.Book
import com.chemtrails.booksapp.databinding.ActivityMainBinding
import com.chemtrails.booksapp.ui.adapter.BooksAdapter

class MainActivity : AppCompatActivity() {

    private val books = mutableListOf<Book>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = BooksAdapter(this, books)
        binding.books.adapter = adapter

        binding.addButton.setOnClickListener {
            if (binding.title.text.isNullOrBlank() || binding.author.text.isNullOrBlank()) {
                Toast.makeText(this, "Please fill out title and author!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            books.add(Book(
                    title = binding.title.text.toString(),
                    author = binding.author.text.toString()
            ))
            binding.title.text.clear()
            binding.author.text.clear()
            adapter.notifyDataSetInvalidated()
        }
    }
}