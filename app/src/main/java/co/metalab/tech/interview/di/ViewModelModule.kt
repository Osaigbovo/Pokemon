package co.metalab.tech.interview.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.metalab.tech.interview.ui.MainViewModel
import co.metalab.tech.interview.utils.ViewModelFactory
import co.metalab.tech.interview.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewmodel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
