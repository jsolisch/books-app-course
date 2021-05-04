package com.chemtrails.booksapp.ui.fragments

import android.content.Intent
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
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint

private val isIsbn = Regex("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?\$)[\\d-]+\$")

@AndroidEntryPoint
class BookAddEditFragment : DialogFragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentBookAddEditBinding
    private val args: BookAddEditFragmentArgs by navArgs()
    private val viewModel: BookAddEditViewModel by viewModels()

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
        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.id = args.book?.id
        viewModel.title.value = args.book?.title
        viewModel.author.value = args.book?.author
        viewModel.isbn.value = args.book?.isbn

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

        binding.scanIsbn.setOnClickListener {
            IntentIntegrator.forSupportFragment(this).apply {
                setBeepEnabled(true)
                setOrientationLocked(true)
                setPrompt("Scan an ISBN")
            }.initiateScan()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result == null || result.contents == null) {
            Toast.makeText(
                this.context,
                "Failed to scan ISBN!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (!isIsbn.matches(result.contents)) {
            Toast.makeText(
                this.context,
                "This is not a valid ISBN!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        viewModel.isbn.value = result.contents
    }
}