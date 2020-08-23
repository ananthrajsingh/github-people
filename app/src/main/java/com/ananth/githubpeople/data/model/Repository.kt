package com.ananth.githubpeople.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Model class to hold the repositories received from API call
 */
@Parcelize
data class Repository(

	@Json(name="language")
	val language: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="forks")
	val forks: Int,

	@Json(name="full_name")
	val fullName: String? = null,

	@Json(name="size")
	val size: Int? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="open_issues_count")
	val openIssuesCount: Int? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="git_url")
	val gitUrl: String? = null,

	@Json(name="downloads_url")
	val downloadsUrl: String? = null,

	@Json(name="html_url")
	val url: String? = null,

	@Json(name="open_issues")
	val openIssues: Int? = null,

	@Json(name="forks_count")
	val forksCount: Int? = null
) : Parcelable
