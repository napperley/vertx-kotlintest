package org.digieng.vertx.kotlintest

import io.vertx.core.http.HttpClient

// Extensions for HttpClient.
// Author - Nick Apperley.

infix fun HttpClient.httpGetOk(path: String): Boolean {
    var result = false

    getNow(path) { /* result = it.statusCode() == HttpStatus.OK.num */ }
    return result
}