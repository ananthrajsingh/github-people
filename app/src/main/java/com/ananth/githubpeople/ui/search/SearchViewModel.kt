package com.ananth.githubpeople.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ananth.githubpeople.data.model.User
import com.ananth.githubpeople.data.network.ApiStatus
import com.ananth.githubpeople.data.network.GithubApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    // This will hold the data to be shown in the list
    private val _usersList = MutableLiveData<List<User?>>()
    val usersList: LiveData<List<User?>>
        get() = _usersList

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // The Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // The Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getSearchResults("ananth")
    }

    fun getSearchResults(searchQuery: String) {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            val result = GithubApi.retrofitService.getSearchResults(query = searchQuery)
            _usersList.value = result.items

            for (user in usersList.value!!) {
                Log.d(TAG, "User ${user?.id}")
            }
        }
    }

    companion object {
        val TAG = SearchViewModel::class.java.simpleName
    }
}