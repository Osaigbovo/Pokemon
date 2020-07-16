package co.metalab.tech.interview.di

import co.metalab.tech.interview.BuildConfig
import co.metalab.tech.interview.data.PokeAPIService
import co.metalab.tech.interview.data.PokemonRepositoryImpl
import co.metalab.tech.interview.domain.PokemonRepository
import co.metalab.tech.interview.data.RetrofitService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitService(moshi: Moshi, okHttpClient: Lazy<OkHttpClient>): PokeAPIService =
        RetrofitService.createService(moshi, okHttpClient, PokeAPIService::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val clientBuilder = OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)

        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Singleton
    @Provides
    internal fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonRepository(pokeAPIService: PokeAPIService): PokemonRepository =
        PokemonRepositoryImpl(pokeAPIService)

}