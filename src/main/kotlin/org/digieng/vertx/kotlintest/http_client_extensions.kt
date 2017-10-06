package org.digieng.vertx.kotlintest

import io.vertx.core.http.HttpClient

// Extensions for HttpClient.
// Author - Nick Apperley.

infix fun HttpClient.httpGetStatus(path: String): Int {
    var result = 0

    // TODO: Change the line below to ensure the function passes the test.
    getNow(path) { /*result = it.statusCode()*/ }
    return result
}