package com.ananth.githubpeople.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ananth.githubpeople.databinding.DetailFragmentBinding
import com.ananth.githubpeople.ui.search.SearchViewModel
import com.ananth.githubpeople.util.autoCleared

class DetailFragment : Fragment() {

    var binding by autoCleared<DetailFragmentBinding>()

    companion object {
        fun newInstance() = DetailFragment()
        val TAG = DetailFragment::class.java.simpleName
    }

    private val viewModel: SearchViewModel by viewModels {
        DetailViewModel.Factory(requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater)

        return binding.root
    }

}