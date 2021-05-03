package com.chemtrails.booksapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "isbn")
    val isbn: String? = null,

    // We use a long in millis for the Date to keep it simple
    // Date conversion is whole topic for it self.
    @ColumnInfo(name = "modified")
    val modified: Long = System.currentTimeMillis()
) : Parcelable
