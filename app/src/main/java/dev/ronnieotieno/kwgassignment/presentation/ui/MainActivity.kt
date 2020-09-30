package dev.ronnieotieno.kwgassignment.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import dev.ronnieotieno.kwgassignment.R


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Since I am using Android Navigation component there is nothing I have to write here, My App contains one activity with multiple
         * Fragments.
         */
    }
}