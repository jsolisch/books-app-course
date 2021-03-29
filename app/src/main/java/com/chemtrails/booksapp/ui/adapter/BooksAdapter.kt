package com.chemtrails.booksapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.chemtrails.booksapp.R
import com.chemtrails.booksapp.data.model.Book

class BooksAdapter(
    private val ctx: AppCompatActivity,
    liveData: LiveData<List<Book>>
) : BaseAdapter() {

    init {
        liveData.observe(ctx) {
            books = it
            this.notifyDataSetChanged()
        }
    }

    private var books = emptyList<Book>()

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