package com.chemtrails.booksapp.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.chemtrails.booksapp.data.model.Book
import com.chemtrails.booksapp.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class BookDaoTest {

    // https://www.youtube.com/watch?v=nOp_CEP_EjM&list=PLQkwcJG4YTCSYJ13G4kVIJ10X5zisB2Lq&index=11

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var bookDao: BookDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        bookDao = db.bookDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun shouldBeAbleToAddBook() = runBlocking(Dispatchers.Main) {
        // given
        val book = Book(
            title = "1984",
            author = "George Orwall"
        )

        // when
        bookDao.addBook(book)

        // then
        val all = bookDao.loadBooks().getOrAwaitValue()
        assertThat(all).contains(book)
    }

    @Test
    fun shouldReplaceBookOnConflict() = runBlocking(Dispatchers.Main) {
        // given
        val book = Book(
            title = "1984",
            author = "George Orwall"
        )
        bookDao.addBook(
            Book(
                id = book.id,
                title = "1983",
                author = "George Orwall"
            )
        )

        // when
        bookDao.addBook(book)

        // then
        val all = bookDao.loadBooks().getOrAwaitValue()
        assertThat(all).hasSize(1)
        assertThat(all).contains(book)
    }

    @Test
    fun shouldGetLastModifiedTimestamp() = runBlocking(Dispatchers.Main) {
        // given
        bookDao.addBook(
            Book(
                title = "1984",
                author = "George Orwall",
                modified = 1000
            )
        )
        bookDao.addBook(
            Book(
                title = "The Animal Farm",
                author = "George Orwall",
                modified = 2000
            )
        )

        // when
        val modified = bookDao.lastModified()

        // then
        assertThat(modified).isEqualTo(2000)
    }

    @Test
    fun shouldRemoveBook() = runBlocking(Dispatchers.Main) {
        // given
        val book1 = Book(
            title = "1984",
            author = "George Orwall",
        )
        val book2 = Book(
            title = "The Animal Farm",
            author = "George Orwall",
        )
        bookDao.addBook(book1)
        bookDao.addBook(book2)

        // when
        bookDao.deleteBook(book1)

        // then
        val all = bookDao.loadBooks().getOrAwaitValue()
        assertThat(all).hasSize(1)
        assertThat(all).contains(book2)
    }
}