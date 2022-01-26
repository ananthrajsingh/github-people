package com.ananth.githubpeople.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ananth.githubpeople.data.model.User
import com.ananth.githubpeople.databinding.SearchItemBinding

/**
 * Adapter to provide a data to views that are displayed by [RecyclerView]
 */
class SearchResultAdapter (private val clickListener: ItemClickListener): ListAdapter<User, SearchResultAdapter.SearchResultViewHolder>(DifferenceCallback) {

    companion object DifferenceCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    class SearchResultViewHolder(private var binding: SearchItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: ItemClickListener, user: User) {
            // Provide the current list items with User object to fill values
            binding.user = user
            // Give the current list item the reference to it's listener
            binding.clickListener = listener
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SearchResultViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchItemBinding.inflate(layoutInflater, parent, false)
                return SearchResultViewHolder(binding)
            }
        }
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs a new [ViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder.from(parent)

    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs to show an item.
     */
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

}

class ItemClickListener(val clickListener: (user: User) -> Unit) {
    fun onClick(user: User) = clickListener(user)
}
