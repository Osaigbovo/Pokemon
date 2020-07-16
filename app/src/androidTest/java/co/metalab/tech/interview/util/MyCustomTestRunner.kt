package co.metalab.tech.interview.util

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import co.metalab.tech.interview.PokeApplicationTest

open class MyCustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, PokeApplicationTest::class.java.name, context)
    }
}
