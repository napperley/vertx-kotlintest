package org.digieng.vertx.kotlintest

import io.kotlintest.TestCaseContext
import io.kotlintest.matchers.fail
import io.kotlintest.specs.FunSpec
import io.vertx.core.Vertx
import io.vertx.ext.unit.impl.TestContextImpl
import io.vertx.kotlin.ext.web.client.WebClientOptions

/**
 * Contains tests for HttpClient extensions.
 * @author Nick Apperley
 */
class HttpClientExtensionsTest : FunSpec() {
    private var vertx: Vertx? = null
    // TODO: Fix the initialisation of testCtx constant (remove the IllegalStateException error).
    private val testCtx = TestContextImpl(mutableMapOf<String, Any>("verticle-name" to "Server"),
        null)

    override fun interceptTestCase(context: TestCaseContext, test: () -> Unit) {
        vertx = Vertx.vertx()
        vertx?.deployVerticle(Server::javaClass.name, testCtx.asyncAssertSuccess())
        test()
        vertx?.close()
        vertx = null
    }

    init {
        test("Function httpGetOk returns true") {
            val path = "/status"
            val async = testCtx.async()
            val client = vertx?.createHttpClient(WebClientOptions(defaultHost = "localhost", defaultPort = 8080))

            if (vertx != null && client != null) assert(client httpGetOk path) else fail(
                "Both vertx and client must not be null")
            async.complete()
        }
    }
}