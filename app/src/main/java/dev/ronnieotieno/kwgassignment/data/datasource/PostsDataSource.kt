package dev.ronnieotieno.kwgassignment.data.datasource

import androidx.paging.PagingSource
import dev.ronnieotieno.kwgassignment.api.PostsApi
import dev.ronnieotieno.kwgassignment.models.Post
import retrofit2.HttpException
import java.io.IOException

const val STARTING_INDEX = 0
const val ENDING_INDEX = 100

/**
 * Paging 3 library which is under Android Jetpack. Used to paginate data.
 */

class PostsDataSource(private val postsApi: PostsApi) :
    PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val startingIndex = params.key ?: STARTING_INDEX
        return try {
            val posts = postsApi.getPosts(params.loadSize, startingIndex)
            val lastItem = posts.last()


            /**
             * Since I am supposed to load more than 25 Items but less than 101 items, Here I am checking if the id is equal or more than
             * 100 so I can stop loading extra items.
             * first time it loads 30 items and with each scroll it loads another 10 items until we have reached 100 items then it stops
             */

            LoadResult.Page(
                data = posts,
                prevKey = if (startingIndex == STARTING_INDEX) null else startingIndex - params.loadSize,
                nextKey = if (lastItem.id >= ENDING_INDEX) null else startingIndex + params.loadSize
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }

    }
}