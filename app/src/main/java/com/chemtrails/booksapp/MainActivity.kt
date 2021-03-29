package com.chemtrails.booksapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

data class Book(val title: String, val author: String)

class MainActivity : AppCompatActivity() {

    private lateinit var booksListView: ListView
    private lateinit var titleEditText: EditText
    private lateinit var authorEditText: EditText
    private lateinit var addButton: Button

    private val books = mutableListOf<Book>()
    private lateinit var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        booksListView = findViewById(R.id.books)
        titleEditText = findViewById(R.id.title)
        authorEditText = findViewById(R.id.author)
        addButton = findViewById(R.id.addButton)

        adapter = BooksAdapter(this, books)
        booksListView.adapter = adapter

        addButton.setOnClickListener {
            if (titleEditText.text.isNullOrBlank() || authorEditText.text.isNullOrBlank()) {
                Toast.makeText(this, "Please fill out title and author!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            books.add(Book(
                    title = titleEditText.text.toString(),
                    author = authorEditText.text.toString()
            ))
            titleEditText.text.clear()
            authorEditText.text.clear()
            adapter.notifyDataSetInvalidated()
        }
    }
}

class BooksAdapter(private val ctx: Context, private val books: List<Book>) : BaseAdapter() {
    override fun getCount(): Int = books.size

    override fun getItem(position: Int): Any = books[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val book = books[position]
        val view = convertView
                ?: LayoutInflater.from(ctx).inflate(R.layout.book_list_item, parent, false)

        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)

        title.text = book.title
        author.text = book.author

        return view
    }
}