package com.technical_challenge.yape.framework.model

import android.os.Bundle

data class RecipeUi(
    val name: String,
    val location: LocationUi
) {
    companion object {
        const val NAME = "recipe_ui_name"
        const val LOCATION = "recipe_ui_location"
    }

    fun toBundle(): Bundle =
        Bundle().apply {
            putString(NAME, name)
            putBundle(LOCATION, location.toBundle())
        }
}

fun Bundle.deBundleRecipeUi(): RecipeUi =
    RecipeUi(
        this.getString(RecipeUi.NAME)!!,
        this.getBundle(RecipeUi.LOCATION)!!.deBundleAddressUi()
    )