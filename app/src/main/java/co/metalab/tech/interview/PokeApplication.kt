package co.metalab.tech.interview

import android.app.Application
import android.content.Context
import co.metalab.tech.interview.di.AppComponent
import co.metalab.tech.interview.di.DaggerAppComponent

open class PokeApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent
            .factory()
            .create(applicationContext)
    }

    companion object {
        @JvmStatic
        fun appComponent(context: Context) =
            (context.applicationContext as PokeApplication).initializeComponent()
    }
}