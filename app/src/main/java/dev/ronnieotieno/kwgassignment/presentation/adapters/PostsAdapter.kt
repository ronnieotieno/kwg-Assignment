package dev.ronnieotieno.kwgassignment.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ronnieotieno.kwgassignment.databinding.PostItemBinding
import dev.ronnieotieno.kwgassignment.models.Post


class PostsAdapter(private val sendId: (Int) -> Unit) :
    PagingDataAdapter<Post, PostsAdapter.PostsViewHolder>(
        PlayersDiffCallback()
    ) {

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data = data, sendId)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {

        return PostsViewHolder(
            PostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    class PostsViewHolder(
        private val binding: PostItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Post?, sendId: (Int) -> Unit) {

            binding.apply {
                body.text = data?.body
                email.text = data?.email
                name.text = data?.name
                root.setOnClickListener {
                    sendId(data?.id!!)
                }
            }

        }
    }

    private class PlayersDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

}