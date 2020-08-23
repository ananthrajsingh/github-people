package com.ananth.githubpeople.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ananth.githubpeople.data.model.Repository
import com.ananth.githubpeople.data.model.User
import com.ananth.githubpeople.databinding.DetailFragmentBinding
import com.ananth.githubpeople.ui.search.ItemClickListener
import com.ananth.githubpeople.ui.search.SearchFragmentDirections
import com.ananth.githubpeople.ui.search.SearchResultAdapter
import com.ananth.githubpeople.util.autoCleared

class DetailFragment : Fragment() {

    var adapter by autoCleared<RepoListAdapter>()

    var binding by autoCleared<DetailFragmentBinding>()

    lateinit var user: User

    companion object {
        fun newInstance() = DetailFragment()
        val TAG = DetailFragment::class.java.simpleName
    }

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.Factory(requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater)

        val user = DetailFragmentArgs.fromBundle(requireArguments()).selectedUser
        this.user = user
        viewModel.getRepositories(user)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = RepoListAdapter(RepoClickListener {
            openInBrowser(it)
        })
        binding.repoList.adapter = adapter
        binding.user = user
        viewModel.getRepositories(user)
    }

    /**
     * Extracts the [Repository.url] from the [Repository] argument and opens the link in the
     * web browser.
     */
    private fun openInBrowser(repository: Repository) {
        var url = repository.url
        if (!url?.startsWith("https://")!! && !url.startsWith("http://")) {
            url = "http://$url"
        }
        Log.d(TAG, "Opening ${repository.name}: $url")
        val openUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(openUrlIntent)
    }
}