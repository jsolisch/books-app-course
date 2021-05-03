package com.chemtrails.booksapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.chemtrails.booksapp.R
import com.chemtrails.booksapp.databinding.FragmentBookDetailsBinding
import com.chemtrails.booksapp.ui.viewmodel.BookDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentBookDetailsBinding
    private val viewModel: BookDetailsViewModel by viewModels()
    private val args: BookDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        binding = FragmentBookDetailsBinding.inflate(inflater, container, false)
        binding.book = args.book
        if (!args.book.isbn.isNullOrBlank()) {
            Glide.with(inflater.context)
                .load("http://covers.openlibrary.org/b/isbn/${args.book.isbn}-M.jpg")
                .centerInside()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.cover)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        viewModel.status.observe(this.viewLifecycleOwner){ status ->
            when(status){
                is BookDetailsViewModel.Status.Processing -> {
                    // TODO implement
                }
                is BookDetailsViewModel.Status.Deleted -> {
                    navController.popBackStack()
                }
                is BookDetailsViewModel.Status.Error -> {
                    Snackbar.make(requireView().rootView, status.message, Snackbar.LENGTH_SHORT ).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu_fragment_book_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_book -> {
                navController.navigate(
                    BookDetailsFragmentDirections.actionBookDetailsFragmentToAddEditFragment(args.book)
                )
                true
            }
            R.id.delete_book -> {
                viewModel.removeBook(args.book)
                true
            }
            else -> false
        }
    }

}