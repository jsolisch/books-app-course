package com.chemtrails.booksapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chemtrails.booksapp.data.model.Book

@Database(entities = [Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}