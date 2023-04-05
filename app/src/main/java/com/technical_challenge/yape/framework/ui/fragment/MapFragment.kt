package com.technical_challenge.yape.framework.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.technical_challenge.yape.R
import com.technical_challenge.yape.adapter.viewmodel.MapViewmodel
import com.technical_challenge.yape.databinding.FragmentMapBinding
import com.technical_challenge.yape.framework.model.RecipeUi
import com.technical_challenge.yape.framework.model.deBundleRecipeUi
import com.technical_challenge.yape.framework.utils.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private val binding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)
    private val viewmodel: MapViewmodel by activityViewModels()

    private lateinit var mapFragment: SupportMapFragment
    private var googleMap: GoogleMap? = null

    private lateinit var recipeUi: RecipeUi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeUi = requireArguments().deBundleRecipeUi()

        initializeMap()
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

        Log.d("MapFragment", "onMapReady successfully called")
        updateLocationUi()
    }

    private fun updateLocationUi() {
        val coordinates = LatLng(recipeUi.location.latitude, recipeUi.location.longitude)
        googleMap?.addMarker(
            MarkerOptions()
                .position(LatLng(recipeUi.location.latitude, recipeUi.location.longitude))
                .title(recipeUi.name)
        ) ?: Log.d("MapFragment", "googleMap object has an issue")
        googleMap
            ?.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15.0f))
        mapFragment.onResume()
    }

    private fun initializeMap() {
        mapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .add(binding.mapView.id, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)
    }
}