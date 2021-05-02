package com.chemtrails.booksapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "books", primaryKeys = ["title", "author"])
data class Book(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String? = null,

    // We use a long in millis for the Date to keep it simple
    // Date conversion is whole topic for it self.
    @ColumnInfo(name = "modified")
    val modified: Long = System.currentTimeMillis()
) : Parcelable
