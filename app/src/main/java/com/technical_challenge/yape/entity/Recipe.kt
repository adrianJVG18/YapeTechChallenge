package com.technical_challenge.yape.entity

import com.google.gson.annotations.SerializedName

/**
 * [Recipe] Entity: represents the core data of a Recipe, this one is meant to be
 * serialized in order to be received from other data source
 */
data class Recipe(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("origin_location")
    val originLocation: String?
)