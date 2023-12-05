package com.example.newsApp

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DataConversion
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val json = Json{
    explicitNulls = false
    ignoreUnknownKeys = true
    encodeDefaults = true
    prettyPrint = true
    coerceInputValues = true
}
fun httpClientBuilder(json: Json):HttpClient = HttpClient(CIO){
    install(ContentNegotiation) {
        json(json)
    }
    install(Logging)
    install(HttpTimeout)
    install(HttpCache)
    install(HttpRequestRetry)
    install(Auth)
    install(DataConversion)
    install(ContentEncoding)
    install(DefaultRequest)
}