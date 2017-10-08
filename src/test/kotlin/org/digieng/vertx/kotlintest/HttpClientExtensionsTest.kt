package org.digieng.vertx.kotlintest

import io.kotlintest.matchers.fail
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.FunSpec
import io.vertx.core.Vertx
import io.vertx.core.http.HttpClient
import io.vertx.core.http.HttpMethod
import io.vertx.kotlin.ext.web.client.WebClientOptions
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking

/**
 * Contains tests for HttpClient extensions.
 * @author Nick Apperley
 */
class HttpClientExtensionsTest : FunSpec() {
    private val httpOk = 200
    private var statusCode = 0
    private var vertx: Vertx? = null

    private fun beforeTest() {
        vertx = Vertx.vertx()
        vertx!!.deployVerticle(Server())
    }

    private fun afterTest() {
        vertx?.close()
        vertx = null
    }

    init {
        testHttpGetStatus()
        testHttpDeleteStatus()
        testHttpPutStatus()
        testHttpPostStatus()
    }

    private suspend fun updateStatusCodeByHttp(
        initialDelay: Long = 1000L,
        path: String,
        data: String = "",
        httpMethod: HttpMethod
    ) {
        var count = 0
        val reqLimit = 5
        @Suppress("JoinDeclarationAndAssignment")
        val client: HttpClient

        if (initialDelay > 0L) delay(initialDelay)
        client = vertx!!.createHttpClient(WebClientOptions(defaultHost = "localhost", defaultPort = 8080))
        while (statusCode == 0) {
            statusCode = when (httpMethod) {
                HttpMethod.POST -> client httpPostStatus (path to data)
                HttpMethod.PUT -> client httpPutStatus (path to data)
                HttpMethod.DELETE -> client httpDeleteStatus (path to data)
                else -> client httpGetStatus path
            }
            if (++count > reqLimit) break
        }
        client.close()
        if (count > reqLimit) fail("Too many requests made by HTTP client to the server")
    }

    private fun testHttpGetStatus() = test("Function httpGetStatus returns correct status") {
        beforeTest()
        runBlocking {
            updateStatusCodeByHttp(path = "/status", httpMethod = HttpMethod.GET)
            println("HTTP Status: $statusCode")
            (statusCode == httpOk) shouldEqual true
        }
        afterTest()
    }

    private fun testHttpPutStatus() = test("Function httpPutStatus returns correct status") {
        beforeTest()
        runBlocking {
            updateStatusCodeByHttp(path = "/status", httpMethod = HttpMethod.PUT)
            println("HTTP Status: $statusCode")
            (statusCode == httpOk) shouldEqual true
        }
        afterTest()
    }

    private fun testHttpDeleteStatus() = test("Function httpDeleteStatus returns correct status") {
        beforeTest()
        runBlocking {
            updateStatusCodeByHttp(path = "/status", httpMethod = HttpMethod.DELETE)
            println("HTTP Status: $statusCode")
            (statusCode == httpOk) shouldEqual true
        }
        afterTest()
    }

    private fun testHttpPostStatus() = test("Function httpPostStatus returns correct status") {
        beforeTest()
        runBlocking {
            updateStatusCodeByHttp(path = "/status", httpMethod = HttpMethod.POST)
            println("HTTP Status: $statusCode")
            (statusCode == httpOk) shouldEqual true
        }
        afterTest()
    }
}