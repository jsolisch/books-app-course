package com.chemtrails.booksapp.hilt

import android.app.Application
import androidx.room.Room
import com.chemtrails.booksapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesAppDatabase(ctx: Application): AppDatabase {
        return Room.databaseBuilder(
            ctx,
            AppDatabase::class.java,
            "books-db"
        ).build()
    }
}