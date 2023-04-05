package com.technical_challenge.yape.framework.model

import android.os.Bundle

data class LocationUi(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        const val LATITUDE = "address_latitude"
        const val LONGITUDE = "address_longitude"
    }

    fun toBundle() : Bundle =
        Bundle().apply {
            putDouble(LATITUDE, latitude)
            putDouble(LONGITUDE, longitude)
        }
}

fun Bundle.deBundleAddressUi(): LocationUi =
    LocationUi(
        this.getDouble(LocationUi.LATITUDE),
        this.getDouble(LocationUi.LONGITUDE)
    )