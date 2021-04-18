package com.chemtrails.booksapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.chemtrails.booksapp.data.model.Book
import com.chemtrails.booksapp.databinding.BookListItemBinding

class BooksAdapter(
    private val ctx: AppCompatActivity,
    liveData: LiveData<List<Book>>
) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: BookListItemBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        liveData.observe(ctx) {
            books = it
            this.notifyDataSetChanged()
        }
    }

    private var books = emptyList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.book = books[position]
    }

    override fun getItemCount() = books.size
}