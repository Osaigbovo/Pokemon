package co.metalab.tech.interview.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// Replacement for AppComponent in android tests
@Singleton
@Component(
    //modules = [AppModule::class, ViewModelModule::class]
)
open interface TestAppComponent : AppComponent {
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }
}
