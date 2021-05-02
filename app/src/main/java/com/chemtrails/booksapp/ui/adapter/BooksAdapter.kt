package com.chemtrails.booksapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chemtrails.booksapp.R
import com.chemtrails.booksapp.data.model.Book
import com.chemtrails.booksapp.databinding.BookListItemBinding

class BooksAdapter(
    private val ctx: LifecycleOwner,
    liveData: LiveData<List<Book>>,
    private val onItemClicked: (book: Book) -> Unit,
) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: BookListItemBinding, val ctx: Context) :
        RecyclerView.ViewHolder(binding.root)

    init {
        liveData.observe(ctx) {
            books = it
            this.notifyDataSetChanged()
        }
    }

    private var books = emptyList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        ctx = parent.context
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.binding.book = book
        holder.itemView.setOnClickListener {
            onItemClicked(book)
        }
        if (book.imageUrl != null) {
            Glide.with(holder.ctx)
                .load(book.imageUrl)
                .centerInside()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.binding.cover)
        }
    }

    override fun getItemCount() = books.size
}