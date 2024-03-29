package com.ananth.githubpeople.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ananth.githubpeople.data.model.Repository
import com.ananth.githubpeople.data.model.User
import com.ananth.githubpeople.data.network.ApiStatus
import com.ananth.githubpeople.data.network.GithubApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel : ViewModel() {

    // This will hold the data to be shown in the recycler view
    private val _repoList = MutableLiveData<List<Repository?>>()
    val repoList: LiveData<List<Repository?>>
        get() = _repoList

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // The Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // The Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    /**
     * Talks to the Github's API using [GithubApi] and fetches the [Repository] list for the
     * passed [User].
     * Sets the [status] and [repoList] which the UI is is listening to, using two way binding.
     */
    fun getRepositories(user: User) {
        coroutineScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                val result = GithubApi.retrofitService.getRepositories(user.login!!)
                _repoList.value = result
                _status.value = ApiStatus.SUCCESS
                Log.d(TAG, "Number of response: ${result?.size}")
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Log.e(TAG, "Error fetching results for user: ${user.login!!}", e)
            }
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object {
        val TAG = DetailViewModel::class.java.simpleName
    }
}