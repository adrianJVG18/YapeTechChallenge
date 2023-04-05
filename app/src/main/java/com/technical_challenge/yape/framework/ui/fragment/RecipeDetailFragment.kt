package com.technical_challenge.yape.framework.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import com.technical_challenge.yape.R
import com.technical_challenge.yape.adapter.viewmodel.RecipeViewmodel
import com.technical_challenge.yape.databinding.FragmentRecipeDetailBinding
import com.technical_challenge.yape.framework.model.LocationUi
import com.technical_challenge.yape.framework.model.RecipeUi
import com.technical_challenge.yape.framework.utils.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RecipeDetailFragment : Fragment(R.layout.fragment_recipe_detail) {

    private val binding: FragmentRecipeDetailBinding by viewBinding(FragmentRecipeDetailBinding::bind)
    private val viewmodel: RecipeViewmodel by activityViewModels()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setupViews()
    }

    private fun setupViews() {
        val recipe = viewmodel.selectedRecipe
        with(binding) {
            recipeNameTv.text = recipe!!.name
            recipeDescriptionTv.text = recipe.description ?: ""
            if (recipe.imageUrl.isNotBlank()) {
                Picasso.get()
                    .load(recipe.imageUrl)
                    .into(binding.recipeImageIv)
            }

            if (viewmodel.hasLocation) {
                showLocationButton.setOnClickListener {
                    goToMapFragment()
                }
            } else {
                showLocationButton.visibility = View.GONE
            }
        }
    }

    private fun goToMapFragment() {
        navController.navigate(
            R.id.action_recipeDetailFragment_to_mapFragment,
            with(viewmodel.selectedRecipe!!) {
                RecipeUi(name, LocationUi(originLocation!!.latitude, originLocation.longitude)).toBundle()
            }
        )
    }

}