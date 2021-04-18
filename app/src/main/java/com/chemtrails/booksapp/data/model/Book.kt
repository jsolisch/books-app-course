package com.chemtrails.booksapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "books", primaryKeys = ["title", "author"])
data class Book(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "author")
    val author: String,

    // We use a long in millis for the Date to keep it simple
    // Date conversion is whole topic for it self.
    @ColumnInfo(name = "modified")
    val modified: Long = System.currentTimeMillis()
)
