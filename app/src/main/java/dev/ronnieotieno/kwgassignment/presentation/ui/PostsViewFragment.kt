package dev.ronnieotieno.kwgassignment.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.ronnieotieno.kwgassignment.R
import dev.ronnieotieno.kwgassignment.databinding.FragmentPostsBinding
import dev.ronnieotieno.kwgassignment.presentation.adapters.PostsAdapter
import dev.ronnieotieno.kwgassignment.presentation.adapters.PostsLoadingStateAdapter
import dev.ronnieotieno.kwgassignment.presentation.viewmodels.PostsFragmentViewModel
import dev.ronnieotieno.kwgassignment.utils.RecyclerViewItemDecoration
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PostsViewFragment : Fragment(R.layout.fragment_posts) {

    private lateinit var binding: FragmentPostsBinding
    private val viewModel: PostsFragmentViewModel by viewModels()
    var job: Job? = null
    private val adapter = PostsAdapter { id: Int -> showIDofClickedItem(id) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostsBinding.bind(view)

        setToolBar()
        setHasOptionsMenu(true)
        setUpAdapter()
        startJob()

    }

    private fun setToolBar() {
        val toolbar: Toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = "Posts"


        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

    }

    private fun startJob() {

        //Setting data to Adapter
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getPosts().collectLatest { data ->
                adapter.submitData(data)
            }

        }
    }

    private fun setUpAdapter() {
        binding.postsRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration())

        }
        binding.postsRecyclerview.adapter = adapter.withLoadStateFooter(
            footer = PostsLoadingStateAdapter { retry() }
        )
        binding.error.setOnClickListener {
            adapter.refresh()
        }


        adapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {

                if (adapter.snapshot().isEmpty()) {
                    binding.progressBar.isVisible = true
                }
                binding.error.isVisible = false

            } else {
                binding.progressBar.isVisible = false


                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (adapter.snapshot().isEmpty()) {
                        binding.error.isVisible = true
                        binding.error.text = requireContext().getString(
                            R.string.error_msg,
                            it.error.localizedMessage
                        )
                    }

                }

            }

        }
    }

    private fun retry() {
        adapter.retry()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.link_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        openUrl()

        return super.onOptionsItemSelected(item)
    }

    private fun openUrl() {
        val uri: Uri = Uri.parse("https://www.kwgsoftworks.com/")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    /**
     * Added this to help the reviewer know the number of items loaded without manually  counting.
     */

    private fun showIDofClickedItem(id: Int) {
        val parentLayout = requireActivity().findViewById<View>(android.R.id.content)
        Snackbar.make(parentLayout, "You clicked an item with Id $id", Snackbar.LENGTH_LONG)
            .show()
    }


}