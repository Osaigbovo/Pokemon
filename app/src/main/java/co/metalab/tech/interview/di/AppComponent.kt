package co.metalab.tech.interview.di

import android.app.Application
import android.content.Context
import co.metalab.tech.interview.ui.MainActivity
import co.metalab.tech.interview.ui.PokemonDetail.PokeDetailFragment
import co.metalab.tech.interview.ui.PokemonList.PokeListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        // with @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(pokeListFragment: PokeListFragment)
    fun inject(pokeDetailFragment: PokeDetailFragment)

    fun inject(application: Application)
}