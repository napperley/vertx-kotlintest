package org.digieng.vertx.kotlintest

import io.kotlintest.KTestJUnitRunner
import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.vertx.core.Vertx
import org.junit.runner.RunWith

// Required to let IntelliJ discover tests.
@Suppress("unused")
@RunWith(KTestJUnitRunner::class)
/**
 * Covers HTTP(S) testing.
 * @author Nick Apperley
 */
abstract class HttpSpec(
    val host: String = "localhost",
    val port: Int = 8080,
    body: HttpSpec.() -> Unit = {}
) : Spec() {
    open protected var vertx: Vertx? = null

    init {
        body()
    }

    operator fun String.invoke(test: () -> Unit): TestCase {
        val tc = TestCase(suite = rootTestSuite, name = this, test = test, config = defaultTestCaseConfig)

        rootTestSuite.addTestCase(tc)
        return tc
    }
}