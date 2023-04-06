package com.technical_challenge.yape.domain.interactions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.technical_challenge.yape.adapter.errors.ErrorMessages
import com.technical_challenge.yape.adapter.model.RecipeMocks
import com.technical_challenge.yape.adapter.viewmodel.RecipeViewmodelTest
import com.technical_challenge.yape.data.repository.Response
import com.technical_challenge.yape.data.repository.recipe.RecipeDto
import com.technical_challenge.yape.data.repository.recipe.RecipeRepository
import com.technical_challenge.yape.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class FetchRecipesUsecaseTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var usecase: FetchRecipesUsecase

    @Mock
    private lateinit var recipeRepository: RecipeRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(RecipeViewmodelTest::class)
        usecase = FetchRecipesUsecase(recipeRepository)
    }

    @Test
    fun `when execute() and request succeed then emit Success Response`() = runTest {
        val mockedRecipesResponse = RecipeMocks.getRecipeDtoResponseA()
        val mockedFetchRecipesUsecase = flow<Response<List<RecipeDto>>> {
            emit(Response.Success(mockedRecipesResponse))
        }
        doReturn(mockedFetchRecipesUsecase).`when`(recipeRepository).getRecipes()

        usecase.execute()

        assert(usecase.resultFlow.first() is Response.Success)
        assert((usecase.resultFlow.first() as Response.Success).data == mockedRecipesResponse)
    }

    @Test
    fun `when execute() and request failed then emit Failed Response`() = runTest {
        val mockedFetchRecipesUsecase = flow<Response<List<RecipeDto>>> {
            emit(Response.Failure(Exception(), ErrorMessages.EMPTY_RESPONSE))
        }
        doReturn(mockedFetchRecipesUsecase).`when`(recipeRepository).getRecipes()

        usecase.execute()

        assert(usecase.resultFlow.first() is Response.Failure)
        assert((usecase.resultFlow.first() as Response.Failure).errorMessage == ErrorMessages.EMPTY_RESPONSE)
    }
}
/*

@Test
fun ``() = runTest {

}

 */