package com.chemtrails.booksapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.chemtrails.booksapp.R
import com.chemtrails.booksapp.data.Book

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