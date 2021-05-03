package com.chemtrails.booksapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.chemtrails.booksapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * For some reason this only works like this? Otherwise you will get a:
         * java.lang.IllegalStateException: Activity com.chemtrails.booksapp.ui.MainActivity@ede67bf does not have a NavController set
         */
        val navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment)!!
            .findNavController()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(
            navController,
            AppBarConfiguration(navController.graph)
        )
    }
}