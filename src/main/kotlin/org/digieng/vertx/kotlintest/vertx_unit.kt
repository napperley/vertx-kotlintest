package org.digieng.vertx.kotlintest

import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.ext.unit.Async
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.impl.TestContextImpl

// Provides extra functionality for vertx-unit that is needed by Kotlin Test.
// Author - Nick Apperley

val testCtx: TestContext = TestContextImpl(mutableMapOf<String, Any>(), null)

fun TestContext.useAsync(block: Async.() -> Unit) {
    val async = async()

    block(async)
    async.complete()
}

fun Vertx.deployTestVerticle(verticle: Verticle) {
    deployVerticle(verticle, testCtx.asyncAssertSuccess())
}