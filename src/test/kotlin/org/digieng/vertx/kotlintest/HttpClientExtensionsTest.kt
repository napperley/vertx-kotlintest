package org.digieng.vertx.kotlintest

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.FunSpec
import io.vertx.core.Vertx
import io.vertx.kotlin.ext.web.client.WebClientOptions

/**
 * Contains tests for HttpClient extensions.
 * @author Nick Apperley
 */
class HttpClientExtensionsTest : FunSpec() {
    private var vertx: Vertx? = null

    private fun beforeTest() {
        vertx = Vertx.vertx()
    }

    private fun afterTest() {
        vertx?.close()
        vertx = null
    }

    init {
        testHttpGetStatus()
    }

    private fun testHttpGetStatus() = test("Function httpGetStatus returns correct status") {
        beforeTest()
        val path = "/status"
        val httpOk = 200

        vertx!!.deployTestVerticle(Server())
        testCtx.useAsync {
            val client = vertx!!.createHttpClient(WebClientOptions(defaultHost = "localhost", defaultPort = 8080))

            shouldBe(client httpGetStatus path == httpOk)
        }
        afterTest()
    }
}