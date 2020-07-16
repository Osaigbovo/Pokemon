package co.metalab.tech.interview.data

import co.metalab.tech.interview.domain.PokemonRepository
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val pokeAPIService: PokeAPIService)
    : PokemonRepository {

    override suspend fun getPokemons(): Result<List<Pokemon>> {
        return try {
            val response = pokeAPIService.getPokemons()
            getResult(response = response, onError = {
                Result.Error(
                    IOException("Error getting pokemons ${response.code()} ${response.message()}")
                )
            })
        } catch (e: Exception) {
            Result.Error(IOException("Error getting pokemons", e))
        }
    }

    override suspend fun getEvolutions(pokeId: Int): Result<List<Evolution>> {
        return try {
            val response = pokeAPIService.getEvolutions(pokeId)
            getResults(response = response, onError = {
                Result.Error(
                    IOException("Error getting evolutions ${response.code()} ${response.message()}")
                )
            })
        } catch (e: Exception) {
            Result.Error(IOException("Error getting evolutions", e))
        }
    }
}

private inline fun getResult(
    response: Response<List<Pokemon>>,
    onError: () -> Result.Error
): Result<List<Pokemon>> {
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) return Result.Success(body)
    }
    return onError.invoke()
}

private inline fun getResults(
    response: Response<List<Evolution>>,
    onError: () -> Result.Error
): Result<List<Evolution>> {
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) return Result.Success(body)
    }
    return onError.invoke()
}