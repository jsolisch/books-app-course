package com.chemtrails.booksapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.chemtrails.booksapp.R
import com.chemtrails.booksapp.databinding.FragmentBookDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBookDetailsBinding
    private val args: BookDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookDetailsBinding.inflate(inflater, container, false)
        binding.book = args.book
        if (args.book.imageUrl != null) {
            Glide.with(inflater.context)
                .load(args.book.imageUrl)
                .centerInside()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.cover)
        }
        return binding.root
    }

}