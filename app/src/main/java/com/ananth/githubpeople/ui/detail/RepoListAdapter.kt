package com.ananth.githubpeople.ui.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ananth.githubpeople.data.model.Repository
import com.ananth.githubpeople.databinding.RepoItemBinding

/**
 * Adapter to provide a [Repository] to views that are displayed by [RecyclerView]
 */
class RepoListAdapter (private val clickListener: RepoClickListener):
    ListAdapter<Repository, RepoListAdapter.RepoListViewHolder>(DifferenceCallback) {

    companion object DifferenceCallback: DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }

    class RepoListViewHolder(private var binding: RepoItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: RepoClickListener, repository: Repository) {
            Log.d("RepoListAdapter", "Binding ${repository.name}")
            binding.repository = repository
            binding.clickListener = listener
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RepoListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RepoItemBinding.inflate(layoutInflater, parent, false)
                return RepoListViewHolder(binding)
            }
        }
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs a new [ViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        return RepoListViewHolder.from(parent)

    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs to show an item.
     */
    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

}

class RepoClickListener(val clickListener: (repository: Repository) -> Unit) {
    fun onClick(repository: Repository) = clickListener(repository)
}
