package com.example.flickr.data

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    NO_DATA
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val msg: String? = null) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
        fun noData() = NetworkState(Status.NO_DATA,"")
    }
}

val LIST = 0
val NO_DATA = 1
val ERROR = 2