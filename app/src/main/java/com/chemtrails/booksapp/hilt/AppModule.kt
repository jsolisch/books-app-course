package com.chemtrails.booksapp.hilt

import android.app.Application
import androidx.room.Room
import com.chemtrails.booksapp.api.BooksApi
import com.chemtrails.booksapp.api.BooksApiServerUrl
import com.chemtrails.booksapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BooksApiServerUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesBooksApiClient(retrofit: Retrofit): BooksApi {
        return retrofit.create(BooksApi::class.java)
    }

}