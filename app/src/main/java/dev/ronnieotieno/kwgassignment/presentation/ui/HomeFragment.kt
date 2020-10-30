package dev.ronnieotieno.kwgassignment.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.ronnieotieno.kwgassignment.R
import dev.ronnieotieno.kwgassignment.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        binding.openPosts.setOnClickListener {
            navigate()
        }
    }

    //Navigating to posts Fragment
    private fun navigate() {
        binding.root.findNavController().navigate(HomeFragmentDirections.toPostsViewFragment())
    }

}