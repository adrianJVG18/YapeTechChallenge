package com.technical_challenge.yape.data.repository.recipe

import com.technical_challenge.yape.data.retrofit.MockableRecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ViewModelComponent::class)
object RecipeRepositoryProvider {

    /**
     * Each time hilt needs to provide an implementation of [RecipeRepository]
     * this method tells it what Implementation must provide.
     *
     * @param recipeRepositoryImpl tells hilt what implementation provide
     */
    @Provides
    fun provideRecipeRepositoryImplementation(
        recipeRepositoryImpl: MockableRecipeRepositoryImpl
    ) : RecipeRepository = recipeRepositoryImpl
}