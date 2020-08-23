package com.ananth.githubpeople.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * The model to hold the response sent by Github API from search endpoint
 */
@Parcelize
data class SearchResponse(

	@Json(name="total_count")
	val totalCount: Int? = null,

	@Json(name="incomplete_results")
	val incompleteResults: Boolean? = null,

	@Json(name="items")
	val items: List<User?>? = null
) : Parcelable
