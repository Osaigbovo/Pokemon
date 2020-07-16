package co.metalab.tech.interview

import co.metalab.tech.interview.di.AppComponent

class PokeApplicationTest : PokeApplication() {

    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent
            .factory()
            .create(this)
    }
}