package com.technical_challenge.yape.framework.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.technical_challenge.yape.R
import com.technical_challenge.yape.adapter.model.Output
import com.technical_challenge.yape.adapter.viewmodel.RecipeViewmodel
import com.technical_challenge.yape.databinding.FragmentHomeBinding
import com.technical_challenge.yape.framework.model.RecipeItemUi
import com.technical_challenge.yape.framework.adapter.RecipesAdapter
import com.technical_challenge.yape.framework.utils.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private val viewmodel: RecipeViewmodel by activityViewModels()
    private lateinit var navController: NavController
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setupViews()
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewmodel.recipes.collect { state ->
                when (state) {
                    is Output.Success -> {
                        handleState(true)
                        binding.swipeRefresh.isRefreshing = false
                        recipesAdapter = RecipesAdapter(
                            state.data.map { RecipeItemUi(it.id, it.name, it.imageUrl) },
                            itemClickListener
                        )
                        binding.recipesListRv.adapter = recipesAdapter
                    }
                    is Output.Failure -> {
                        binding.swipeRefresh.isRefreshing = false
                        handleState(false)
                    }
                    else -> {
                        if (state is Output.Loading && state.isLoading) {
                            with(binding) {
                                recipesListRv.visibility = View.GONE
                                errorStateViewGroup.visibility = View.GONE
                                loadingProgressBar.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private val itemClickListener = object : RecipesAdapter.OnItemClickListener {
        override fun onItemClick(item: RecipeItemUi) {
            viewmodel.updateSelectedRecipe(item.id)
            navController.navigate(
                R.id.action_homeFragment_to_recipeDetailFragment
            )
        }

    }

    private fun setupViews() {
        if (viewmodel.recipes.value !is Output.Success) {
            viewmodel.fetchRecipes()
        }
        with(binding) {
            recipesListRv.layoutManager = LinearLayoutManager(requireContext())
            swipeRefresh.setOnRefreshListener {
                viewmodel.fetchRecipes()
            }
        }
    }

    private fun handleState(isSuccess: Boolean) {
        with(binding) {
            recipesListRv.visibility = if (isSuccess) View.VISIBLE else View.GONE
            errorStateViewGroup.visibility = if (isSuccess) View.GONE else View.VISIBLE
            loadingProgressBar.visibility = View.GONE
        }
    }

}