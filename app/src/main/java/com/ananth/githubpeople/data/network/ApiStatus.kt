package com.ananth.githubpeople.data.network

/**
 * The class to classify the [GithubApi] response status. This will
 * help in handling the status in an elegant fashion.
 */
enum class ApiStatus {
    LOADING,
    ERROR,
    SUCCESS
}