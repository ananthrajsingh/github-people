package com.ananth.githubpeople.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ananth.githubpeople.ui.search.SearchViewModel

class DetailViewModel(app: Application) : AndroidViewModel(app) {



    /**
     * Factory for constructing [DetailViewModel] with parameter
     */
    class Factory(private val app: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}