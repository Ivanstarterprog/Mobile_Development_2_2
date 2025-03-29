package com.example.mobile_development_2_2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mobile_development_2_2.UI.MainMenuViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

val rickSanchez = ResultOfCharactersQueue(
    id = 1,
    name = "Rick Sanchez",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Male",
    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
)

val fakeResponse = RickAndMortyCharactersData(
    info = Info(1, 2, null, null),
    results = arrayListOf(rickSanchez)
)

@ExperimentalCoroutinesApi
class UnitTests {
    private val sampleDispatcher = Dispatchers.Unconfined
    private lateinit var characterRepository: ICharacterRepository
    private lateinit var viewModel: MainMenuViewModel


    @get:Rule
    val instantTaskExecutorRule : TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(sampleDispatcher)
        characterRepository = mock(ICharacterRepository::class.java)
        viewModel = MainMenuViewModel(characterRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Проверка ответа API`(){
        CoroutineScope(sampleDispatcher).launch {
            val requester = RickAndMortyApi.getInstance().create(GetCharacters::class.java)
            val getter = requester.getAllCharacters(1)

            assertEquals(getter.body()!!.results[0].name, "Rick Sanchez")
        }
    }

    @Test
    fun `Проверка отображения ошибки`() = runBlocking {
        `when`(characterRepository.getCharacters(1)).thenThrow(RuntimeException("Site"))

        viewModel.fetchCharactersRequest()

        val observer = Observer<String> {}
        viewModel.errorText.observeForever(observer)
        assertEquals("Site", viewModel.errorText.value)
        viewModel.errorText.removeObserver(observer)
    }

    @Test
    fun `Проверка на то, что API отдал корректные данные`() = runBlocking {
        val result : Result<RickAndMortyCharactersData> = Result.success(fakeResponse)
        `when`(characterRepository.getCharacters(1)).thenReturn(result)
        viewModel.pickPage(1)
        viewModel.fetchCharactersRequest()

        val observer = Observer<RickAndMortyCharactersData> {}
        viewModel.items.observeForever(observer)
        assertEquals(fakeResponse, viewModel.items.value )
        viewModel.items.removeObserver(observer)
    }

}