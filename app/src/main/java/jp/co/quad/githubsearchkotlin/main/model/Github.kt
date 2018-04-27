package jp.co.quad.githubsearchkotlin.model

import com.google.gson.annotations.SerializedName

data class RepoResponse(
        @SerializedName("total_count") val totalCount: Int,
        @SerializedName("incomplete_results") val incompleteResults: Boolean,
        @SerializedName("items") val items: List<RepoItem>?
)

data class RepoItem(
        @SerializedName("full_name") val fullName: String
)