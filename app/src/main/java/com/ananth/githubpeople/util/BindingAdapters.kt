package com.ananth.githubpeople.util

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ananth.githubpeople.R
import com.ananth.githubpeople.data.model.Repository
import com.ananth.githubpeople.data.model.User
import com.ananth.githubpeople.ui.detail.RepoListAdapter
import com.ananth.githubpeople.ui.search.SearchResultAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


/**
 * Show the image associated to the url in the view with the help of [Glide].
 */
@BindingAdapter("image_url")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    // If results don't have url for thumbnail,
    // then broken placeholder image will be shown.
    if (imgUrl == null || imgUrl == "") {
        imgView.setImageResource(R.drawable.ic_broken_image)
    }
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

/**
 * Passes the list to the [SearchResultAdapter] for the [RecyclerView]
 */
@BindingAdapter("search_list_data")
fun bindSearchRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as SearchResultAdapter?
    adapter?.submitList(data) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}

/**
 * Passes the list to the [RepoListAdapter] for the [RecyclerView]
 */
@BindingAdapter("repo_list_data")
fun bindRepoRecyclerView(recyclerView: RecyclerView, data: List<Repository>?) {
    val adapter = recyclerView.adapter as RepoListAdapter?
    adapter?.submitList(data) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}


/**
 * Show the view only if the boolean expression passed is true
 */
@BindingAdapter("visible_if_true")
fun View.showIfTrue(boolean: Boolean) {
    visibility = if (boolean) View.VISIBLE else View.GONE
}


