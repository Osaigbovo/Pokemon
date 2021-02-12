package co.metalab.tech.interview

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See [co.metalab.tech.interview.util.PokeTestRunner].
 */
class PokeApplicationTest : PokeApplication() {

/*    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent
            .factory()
            .create(this)
    }*/
}