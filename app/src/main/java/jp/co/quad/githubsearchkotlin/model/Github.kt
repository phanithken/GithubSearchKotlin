package jp.co.quad.githubsearchkotlin.model

import com.google.gson.JsonArray

object Model {
    data class Result(val items: JsonArray)
}