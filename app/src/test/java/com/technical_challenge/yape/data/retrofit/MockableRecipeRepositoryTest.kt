package com.technical_challenge.yape.data.retrofit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.technical_challenge.yape.adapter.model.RecipeMocks
import com.technical_challenge.yape.adapter.viewmodel.RecipeViewmodelTest
import com.technical_challenge.yape.data.repository.Response
import com.technical_challenge.yape.data.repository.recipe.RecipeDto
import com.technical_challenge.yape.entity.RecipesResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
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
class MockableRecipeRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var mockableRepository: MockableRecipeRepositoryImpl

    @Mock
    private lateinit var recipeApi: MockableRecipeApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(RecipeViewmodelTest::class)
        mockableRepository = MockableRecipeRepositoryImpl(recipeApi)
    }

    @Test
    fun `when getRecipes() and request Succeed then emit Success Response`() = runTest {
        val mockedList: RecipesResponse = RecipeMocks.getRecipeResponseEntity()
        val mappedList: List<RecipeDto> = mockedList.recipes.map {
            RecipeDto(
                id = it.id,
                name = it.name,
                description = it.description!!,
                imageUrl = it.imageUrl!!,
                originLocation = mockableRepository.getLocation(it.originLocation)
            )
        }

        doReturn(mockedList).`when`(recipeApi).getAllRecipes()

        val emitted = mockableRepository.getRecipes().toList()

        assert(emitted[0] is Response.Loading)
        assert((emitted[0] as Response.Loading).isLoading)
        assert(emitted[1] is Response.Success)
        assert((emitted[1] as Response.Success).data.containsAll(mappedList))
    }

    @Test
    fun `when getRecipes() and request Fails then emit Failed Response`() = runTest {
        doReturn(Exception("Connection Mock error")).`when`(recipeApi).getAllRecipes()

        val emitted = mockableRepository.getRecipes().toList()

        assert(emitted[1] is Response.Failure)
    }

}

/* Empty test Template

@Test
fun ``() = runTest {

}

 */