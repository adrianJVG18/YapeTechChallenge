package com.technical_challenge.yape.adapter.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.technical_challenge.yape.adapter.errors.EmptyResponseError
import com.technical_challenge.yape.adapter.model.Output
import com.technical_challenge.yape.adapter.model.RecipeMocks
import com.technical_challenge.yape.data.repository.Response
import com.technical_challenge.yape.data.repository.recipe.RecipeDto
import com.technical_challenge.yape.domain.interactions.FetchRecipesUsecase
import com.technical_challenge.yape.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class RecipeViewmodelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewmodel: RecipeViewmodel

    @Mock
    private lateinit var fetchRecipesUsecase: FetchRecipesUsecase

    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(RecipeViewmodelTest::class)
        viewmodel = RecipeViewmodel(fetchRecipesUsecase)
    }

    @Test
    fun `when fetchRecipes() then recipes must start executing fetchRecipesUsecase`() = runTest {
        viewmodel.fetchRecipes()
        verify(fetchRecipesUsecase).execute(any())
        assert(viewmodel.recipes.value is Output.Loading)
        assert((viewmodel.recipes.value as Output.Loading).isLoading)
    }

    @Test
    fun `when fetchRecipes() and request succeed then emit Success Output`() = runTest {
        val mockedRecipesResponse = RecipeMocks.getRecipeDtoResponseA()
        val mockedFetchRecipesUsecase = flow<Response<List<RecipeDto>>> {
            emit(Response.Success(mockedRecipesResponse))
        }
        doReturn(mockedFetchRecipesUsecase).`when`(fetchRecipesUsecase).execute(any())

        viewmodel.fetchRecipes()

        assert(viewmodel.recipes.value is Output.Success)
        assert((viewmodel.recipes.value as Output.Success).data ==
                mockedRecipesResponse.map { viewmodel.convertToRecipeModel(it) })

    }

    @Test
    fun `when fetchRecipes() and request failed then emit Failed Output`() = runTest {
        val mockedFetchRecipesUsecase = flow<Response<List<RecipeDto>>> {
            emit(Response.Failure(Exception(), RecipeMocks.FAILED_FETCHING_ERROR_MESSAGE))
        }
        doReturn(mockedFetchRecipesUsecase).`when`(fetchRecipesUsecase).execute(any())

        viewmodel.fetchRecipes()

        assert(viewmodel.recipes.value is Output.Failure)
        assert((viewmodel.recipes.value as Output.Failure).errorMessage == RecipeMocks.FAILED_FETCHING_ERROR_MESSAGE)
    }

    @Test
    fun `when fetchRecipes() and request succeed into empty response emit Failed Output with EmptyResponseError`() = runTest {
        val mockedRecipesResponse = emptyList<RecipeDto>()
        val mockedFetchRecipesUsecase = flow<Response<List<RecipeDto>>> {
            emit(Response.Success(mockedRecipesResponse))
        }
        doReturn(mockedFetchRecipesUsecase).`when`(fetchRecipesUsecase).execute(any())

        viewmodel.fetchRecipes()

        assert(viewmodel.recipes.value is Output.Failure)
        assert((viewmodel.recipes.value as Output.Failure).error is EmptyResponseError)
    }

    @Test
    fun `when updateSelectedRecipe(id) then selectedRecipe is a RecipeModel!!`() = runTest {
        val mockedRecipesResponse = RecipeMocks.getRecipeDtoResponseA()
        val mockedFetchRecipesUsecase = flow<Response<List<RecipeDto>>> {
            emit(Response.Success(mockedRecipesResponse))
        }
        doReturn(mockedFetchRecipesUsecase).`when`(fetchRecipesUsecase).execute(any())
        viewmodel.fetchRecipes()

        viewmodel.updateSelectedRecipe("2")

        assert(viewmodel.selectedRecipe?.id == RecipeMocks.getRecipeDtoResponseA().find { it.id == "2" }?.id)
    }
}


/* Empty test Template

@Test
fun ``() = runTest {

}

 */