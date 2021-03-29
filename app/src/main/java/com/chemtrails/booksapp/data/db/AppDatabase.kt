package com.chemtrails.booksapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chemtrails.booksapp.data.model.Book

@Database(entities = [Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(appCtx: Context) = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                appCtx,
                AppDatabase::class.java,
                "books-db"
            ).build().also { instance = it }
        }
    }
}