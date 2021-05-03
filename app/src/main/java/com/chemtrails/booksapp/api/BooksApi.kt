package com.chemtrails.booksapp.api

import com.chemtrails.booksapp.data.model.Book
import retrofit2.Response
import retrofit2.http.*
import java.util.*

// You may have to change this to your local address.
const val BooksApiServerUrl = "http://192.168.178.74:9001"

interface BooksApi {
    @GET("api/books")
    suspend fun getBooks(@Query("since") since: Long?): Response<List<Book>>

    @POST("api/books")
    suspend fun addBook(@Body book: Book): Response<Void>

    @DELETE("api/books")
    suspend fun deleteBook(@Query("id") id: String): Response<Void>
}