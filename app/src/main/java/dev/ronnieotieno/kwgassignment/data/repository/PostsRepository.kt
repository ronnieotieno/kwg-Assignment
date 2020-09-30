package dev.ronnieotieno.kwgassignment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.ronnieotieno.kwgassignment.api.PostsApi
import dev.ronnieotieno.kwgassignment.data.datasource.PostsDataSource
import dev.ronnieotieno.kwgassignment.models.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

const val NETWORK_PAGE_SIZE = 10

/**
 * Repository used to access data being loaded from network call
 */
@Singleton
class PostsRepository @Inject constructor(private val postsApi: PostsApi) {

    fun getPosts(): Flow<PagingData<Post>> = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
        pagingSourceFactory = {
            PostsDataSource(postsApi)
        }
    ).flow

}