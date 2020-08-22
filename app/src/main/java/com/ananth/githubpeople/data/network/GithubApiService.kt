package com.ananth.githubpeople.data.network

import com.ananth.githubpeople.data.model.Repository
import com.ananth.githubpeople.data.model.SearchResponse
import com.ananth.githubpeople.util.Constant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * The [Moshi] object that Retrofit will be using, Kotlin adapter is added for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/**
 * The Retrofit builder to build a retrofit object using a Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constant.BASE_URL)
    .build()

interface GithubApiService {

    /**
     * Suspend function that gets all the users for the passed query
     */
    @GET("search/users")
    suspend fun getSearchResults(
        @Query("q") query: String
    ): SearchResponse

    /**
     * Suspend function to give all repository of a particular user, identified by [username]
     */
    @GET("users/{username}/repos")
    suspend fun getRepositories(
        @Path(value = "username") username: String
    ): List<Repository?>?

}

/**
 * Public Api object that exposes the lazy-initialized Retrofit service.
 */
object GithubApi {
    val retrofitService: GithubApiService by lazy { retrofit.create(GithubApiService::class.java) }
}