package com.chemtrails.booksapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.chemtrails.booksapp.R
import com.chemtrails.booksapp.data.model.Book
import com.chemtrails.booksapp.databinding.FragmentMainBinding
import com.chemtrails.booksapp.ui.adapter.BooksAdapter
import com.chemtrails.booksapp.ui.viewmodel.BooksViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        val viewModel: BooksViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = BooksAdapter(this.viewLifecycleOwner, viewModel.books, { book ->
            navController.navigate(
                MainFragmentDirections.actionMainFragmentToBookDetailsFragment(book)
            )
        })
        binding.books.adapter = adapter

        // Note: This is important you should never use UI views in the ViewModel!
        viewModel.toast.observe(this.viewLifecycleOwner) {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        }

        binding.addButton.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_addEditFragment)
        }
    }

}