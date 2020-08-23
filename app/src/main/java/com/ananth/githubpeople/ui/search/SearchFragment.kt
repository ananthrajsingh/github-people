package com.ananth.githubpeople.ui.search

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ananth.githubpeople.R
import com.ananth.githubpeople.databinding.SearchFragmentBinding
import com.ananth.githubpeople.util.autoCleared

class SearchFragment : Fragment() {

    var adapter by autoCleared<SearchResultAdapter>()

    var binding by autoCleared<SearchFragmentBinding>()

    companion object {
        fun newInstance() = SearchFragment()
        val TAG = SearchFragment::class.java.simpleName
    }

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.Factory(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = SearchFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        adapter = SearchResultAdapter(ItemClickListener {
            this.findNavController().navigate(SearchFragmentDirections
                .actionSearchFragmentToDetailFragment(it))
        })
        binding.searchList.adapter = adapter
        initSearchInputListener()
    }

    /**
     * Starts to listen to the [EditText] in which the search query is entered by the user.
     */
    private fun initSearchInputListener() {
        binding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                Log.d(TAG, "Search Requested")
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(v: View) {
        val query = binding.input.text.toString()
        viewModel.setQuery(query)
        // Dismiss keyboard
        dismissKeyboard(v.windowToken)
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

}