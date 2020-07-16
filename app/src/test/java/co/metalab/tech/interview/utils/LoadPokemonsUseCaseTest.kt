package co.metalab.tech.interview.utils

import co.metalab.tech.interview.domain.LoadPokemonUseCase
import co.metalab.tech.interview.domain.PokemonRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import java.io.IOException
import co.metalab.tech.interview.data.Result
import co.metalab.tech.interview.utils.util.TestDataGenerator.Companion.generatePokemons
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for [LoadPokemonUseTest] mocking the dependencies.
 */
@RunWith(JUnit4::class)
class LoadPokemonUseTest {

    private val pokemonRepository: PokemonRepository = mock()
    private val loadPokemonUseCase = LoadPokemonUseCase(pokemonRepository)

    @Test
    fun `Test Load All Pokemons Success`() = runBlocking {
        // Arrange - Given that a list of pokemons is returned.
        val expected = Result.Success(generatePokemons())
        whenever(pokemonRepository.getPokemons())
            .thenReturn(expected)

        // Act - When loading pokemons.
        val result = loadPokemonUseCase.invoke()

        // Assert - Then the result was triggered
        assertNotNull(result)
        assertEquals(expected, result)
    }

    @Test
    fun `Test Load All Pokemons Error`() = runBlocking {
        // Arrange - Given that an error is returned.
        val expected = Result.Error(IOException("error"))
        whenever(pokemonRepository.getPokemons())
            .thenReturn(expected)

        // Act - When loading pokemon
        val result = loadPokemonUseCase.invoke()

        // Assert - Then an error is return
        assertEquals(expected, result)
    }
}
