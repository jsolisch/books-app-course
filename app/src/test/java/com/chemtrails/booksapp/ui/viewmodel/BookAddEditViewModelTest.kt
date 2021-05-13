package com.chemtrails.booksapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.chemtrails.booksapp.data.model.Book
import com.chemtrails.booksapp.data.repository.BookRepository
import com.chemtrails.booksapp.utils.MainCoroutineRule
import com.chemtrails.booksapp.utils.await
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class BookAddEditViewModelTest {

    @get:Rule
    var taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    @ExperimentalCoroutinesApi
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var repository: BookRepository
    private lateinit var viewModel: BookAddEditViewModel


    @Before
    fun setup() {
        viewModel = BookAddEditViewModel(repository)
    }

    @Test
    fun `Should fail with error if title is not set`() {
        // given
        viewModel.title.value = ""

        // when
        viewModel.addBook()

        // then
        assertThat(
            viewModel.status.await()
        ).isEqualTo(
            BookAddEditViewModel.Status.Error("Please fill in title and author!")
        )
    }

    @Test
    fun `Should fail with error if author is not set`() {
        // given
        viewModel.title.value = "1984"
        viewModel.author.value = ""

        // when
        viewModel.addBook()

        // then
        assertThat(
            viewModel.status.await()
        ).isEqualTo(
            BookAddEditViewModel.Status.Error("Please fill in title and author!")
        )
    }

    @Test
    fun `Should fail to add book on exception`() = runBlocking {
        // given
        `when`(repository.addBook(any() ?: Book(title = "", author = ""))).thenThrow(
            RuntimeException("Boom!")
        )

        viewModel.title.value = "1984"
        viewModel.author.value = "George Orwall"

        // when
        viewModel.addBook()

        // then
        assertThat( // Status: Error
            viewModel.status.await()
        ).isEqualTo(
            BookAddEditViewModel.Status.Error("Could not save book!")
        )
        assertThat( // Do not reset
            viewModel.title.await()
        ).isEqualTo(
            "1984"
        )
        assertThat( // Do not reset
            viewModel.author.await()
        ).isEqualTo(
            "George Orwall"
        )
        verify(repository, times(1)).addBook(
            argThat { it?.title == "1984" && it.author == "George Orwall" }
                ?: Book(title = "", author = "")
        )
    }

    @Test
    fun `Should add book`() = runBlocking {
        // given
        viewModel.title.value = "1984"
        viewModel.author.value = "George Orwall"

        // when
        viewModel.addBook()

        // then
        assertThat(
            viewModel.status.await()
        ).isEqualTo(
            BookAddEditViewModel.Status.Success("Successfully added book!")
        )
        assertThat(
            viewModel.title.await()
        ).isEqualTo(
            ""
        )
        assertThat(
            viewModel.author.await()
        ).isEqualTo(
            ""
        )
        verify(repository, times(1)).addBook(
            argThat { it?.title == "1984" && it.author == "George Orwall" }
                ?: Book(title = "", author = "")
        )
    }
}