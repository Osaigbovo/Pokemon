# Notes

Feel free to write down any and all thoughts, ideas, considerations and tradeoffs that you will likely experience during the test.

# Pokedex
The Pokedex displays a list of Pokemons, and certain details about each Pokemon.

## Features
* CLEAN Architecture (+ MVVM architecture)
* Dependency injection with Dagger 2
* Kotlin Coroutine
* Retrofit 2
* ViewState
* RecycledView state is saved onConfigurationChanged
* RecyclerView is Filterable
* Material design

## Screenshots
<img src="../master/designs/list.png" width="240"> <img src="../master/designs/search.png" width="240"> <img src="../master/designs/detail.png" width="240">

## Libraries Used
* [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
* [Dagger 2](https://github.com/google/dagger)
* [Glide](https://github.com/bumptech/glide) - for loading and caching images
* [Retrofit 2](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc.
* [OkHttp](https://github.com/square/okhttp)
* [Moshi](https://github.com/square/moshi) - a modern JSON library for Android and Java. It makes it easy to parse JSON into Java objects
* [AndroidX](https://developer.android.com/jetpack/androidx/)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [Navigation Component](https://developer.android.com/guide/navigation)
* [Espresso](https://developer.android.com/training/testing/espresso/) Use Espresso to write concise, beautiful, and reliable Android UI tests.

## Considerations
I would have liked to use TDD, but I had a bug early on that was taking so much of my time.
