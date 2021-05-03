package com.chemtrails.booksapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chemtrails.booksapp.data.model.Book
import java.util.*

@Database(entities = [Book::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}