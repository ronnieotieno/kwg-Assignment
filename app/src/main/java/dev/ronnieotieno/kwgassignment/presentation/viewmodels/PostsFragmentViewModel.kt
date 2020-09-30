package dev.ronnieotieno.kwgassignment.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.ronnieotieno.kwgassignment.data.repository.PostsRepository
import dev.ronnieotieno.kwgassignment.models.Post
import kotlinx.coroutines.flow.Flow

class PostsFragmentViewModel @ViewModelInject constructor(private val postsRepository: PostsRepository) :
    ViewModel() {

    private var currentResult: Flow<PagingData<Post>>? = null

    // Getting data as flow

    fun getPosts(): Flow<PagingData<Post>> {
        val newResult: Flow<PagingData<Post>> =
            postsRepository.getPosts().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

}