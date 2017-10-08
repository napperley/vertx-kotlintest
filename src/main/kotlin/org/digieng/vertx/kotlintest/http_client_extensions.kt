package org.digieng.vertx.kotlintest

import io.vertx.core.http.HttpClient

// Extensions for HttpClient.
// Author - Nick Apperley.

infix fun HttpClient.httpGetStatus(path: String): Int {
    var result = 0

    getNow(path) {
        result = it.statusCode()
    }
    return result
}

infix fun HttpClient.httpPostStatus(args: Pair<String, String>): Int {
    var result = 0
    val reqUrl = args.first
    val data = args.second

    post(reqUrl) {
        /* result = it.statusCode() */
    }.end(data)
    return result
}

infix fun HttpClient.httpPutStatus(args: Pair<String, String>): Int {
    var result = 0
    val reqUrl = args.first
    val data = args.second

    put(reqUrl) {
        /* result = it.statusCode() */
    }.end(data)
    return result
}

infix fun HttpClient.httpDeleteStatus(args: Pair<String, String>): Int {
    var result = 0
    val reqUrl = args.first
    val data = args.second

    delete(reqUrl) {
        /* result = it.statusCode() */
    }.end(data)
    return result
}