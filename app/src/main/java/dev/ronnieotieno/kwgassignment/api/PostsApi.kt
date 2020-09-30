package dev.ronnieotieno.kwgassignment.api

import dev.ronnieotieno.kwgassignment.models.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsApi {

    /**
     * Calling the EndPoint, I researched and found out that JsonPlaceHolder has pagination and I implemented the feature with
     * _limit and _start query parameters as below
     */
    @GET("comments")
    suspend fun getPosts(
        @Query("_limit") limit: Int?,
        @Query("_start") start: Int?
    ): List<Post>
}