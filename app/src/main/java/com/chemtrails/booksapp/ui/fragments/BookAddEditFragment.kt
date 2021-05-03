package com.chemtrails.booksapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chemtrails.booksapp.databinding.FragmentBookAddEditBinding
import com.chemtrails.booksapp.ui.viewmodel.BookAddEditViewModel
import com.chemtrails.booksapp.ui.viewmodel.BooksViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookAddEditFragment : DialogFragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentBookAddEditBinding
    private val args: BookAddEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        val viewModel: BookAddEditViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.id = args.book?.id
        viewModel.title.value = args.book?.title
        viewModel.author.value = args.book?.author
        viewModel.isbn = args.book?.isbn

        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                is BookAddEditViewModel.Status.Success -> {
                    Toast.makeText(this.context, status.message, Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
                is BookAddEditViewModel.Status.Error -> Toast.makeText(
                    this.context,
                    status.message,
                    Toast.LENGTH_SHORT
                ).show()
                is BookAddEditViewModel.Status.Cancel -> navController.popBackStack()
            }
        }
    }


}