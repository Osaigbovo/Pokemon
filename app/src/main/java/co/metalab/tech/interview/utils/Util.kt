package co.metalab.tech.interview.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import co.metalab.tech.interview.data.Pokemon

var pokemonsCache: Map<Int, Pokemon> = hashMapOf()

fun getPokemonCache(pokemonMap: Map<Int, Pokemon>): Map<Int, Pokemon> {
    pokemonsCache = pokemonMap
    return pokemonsCache
}

fun hideKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    // Check if no view has focus
    val currentFocusedView = activity.currentFocus
    currentFocusedView?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}