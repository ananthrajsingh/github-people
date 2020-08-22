package com.ananth.githubpeople.ui.search

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.*
import com.ananth.githubpeople.data.model.User
import com.ananth.githubpeople.data.network.ApiStatus
import com.ananth.githubpeople.data.network.GithubApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class SearchViewModel(
    app: Application
) : AndroidViewModel(app) {

    // This will hold the data to be shown in the list
    private val _usersList = MutableLiveData<List<User?>>()
    val usersList: LiveData<List<User?>>
        get() = _usersList

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // Checks if previous query wasn't same are current, therefore avoiding new call
    private val _query = MutableLiveData<String>()
    val query: LiveData<String>
        get() = _query


    // The Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // The Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {

    }

    /**
     * Talks to the Github's API using [GithubApi] and fetches the results for the
     * passed [query].
     * Sets the [status] and [usersList] which the UI is is listening to, using two way binding.
     */
    private fun getSearchResults(searchQuery: String) {
        coroutineScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                val result = GithubApi.retrofitService.getSearchResults(query = searchQuery)
                _usersList.value = result.items
                _status.value = ApiStatus.SUCCESS
                Log.d(TAG, "Number of response: ${result.items?.size}")
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Log.e(TAG, "Error fetching results for query: $query", e)
            }
        }
    }

    /**
     * Checks [originalInput], tries to refresh the list with the results for [originalInput].
     * Avoids refresh if current and previous query are same.
     */
    fun setQuery(originalInput: String) {
        if (TextUtils.isEmpty(originalInput)) {
            _query.value = originalInput
            return
        }
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query.value) {
            return
        }
        _query.value = input
        getSearchResults(input)
    }


    companion object {
        val TAG = SearchViewModel::class.java.simpleName
    }

    /**
     * Factory for constructing [SearchViewModel] with parameter
     */
    class Factory(private val app: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}