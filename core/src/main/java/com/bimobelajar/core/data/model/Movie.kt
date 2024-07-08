package com.bimobelajar.core.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: String,
    val title: String,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("release_date")
    val release_date: String,
    val overview: String
)
