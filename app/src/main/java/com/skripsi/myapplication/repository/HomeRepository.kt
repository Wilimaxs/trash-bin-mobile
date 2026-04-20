package com.skripsi.myapplication.repository

import com.google.gson.Gson
import com.skripsi.myapplication.BuildConfig
import com.skripsi.myapplication.core.network.ApiService
import com.skripsi.myapplication.model.ConnectRequest
import com.skripsi.myapplication.model.SessionData
import com.skripsi.myapplication.model.StreamUpdateData
import com.skripsi.myapplication.core.network.NetworkResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val okHttpClient: OkHttpClient,
) : BaseRepository() {

    suspend fun connectSession(qrCode: String): NetworkResult<SessionData> {
        return safeApiCall { apiService.connectSession(ConnectRequest(qrCode)) }
    }

    suspend fun disconnectSession(qrCode: String): NetworkResult<Any?> {
        return safeApiCall { apiService.disconnectSession(ConnectRequest(qrCode)) }
    }

    fun streamDashboard(): Flow<StreamUpdateData> = callbackFlow {

        val request = Request.Builder()
            .url("${BuildConfig.BASE_URL}stream/dashboard")
            .header("Accept", "text/event-stream")
            .header("Cache-Control", "no-cache")
            .header("Connection", "keep-alive")
            .header("Accept-Encoding", "identity") // <-- Mematikan kompresi (Gzip) yang menyebabkan buffering
            .build()

        // Clone okHttpClient to remove read timeout for long-lived SSE connection
        val sseClient = okHttpClient.newBuilder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .protocols(listOf(okhttp3.Protocol.HTTP_1_1))
            .build()

        val factory = EventSources.createFactory(sseClient)
        val eventSource = factory.newEventSource(request, object : EventSourceListener() {
            override fun onOpen(eventSource: EventSource, response: okhttp3.Response) {
                super.onOpen(eventSource, response)
                Timber.d("SSE Connection Opened")
            }

            override fun onEvent(
                eventSource: EventSource,
                id: String?,
                type: String?,
                data: String
            ) {
                Timber.d("SSE Event received: $data")
                try {
                    val update = Gson().fromJson(data, StreamUpdateData::class.java)
                    trySend(update)
                } catch (e: Exception) {
                    Timber.e(e, "SSE Parsing Error")
                }
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: okhttp3.Response?) {
                super.onFailure(eventSource, t, response)
                Timber.e(t, "SSE Connection Failed: ${response?.code}")
                close(t ?: Exception("SSE Connection Failed"))
            }

            override fun onClosed(eventSource: EventSource) {
                super.onClosed(eventSource)
                Timber.d("SSE Connection Closed")
                close()
            }
        })

        awaitClose {
            eventSource.cancel()
        }
    }.flowOn(kotlinx.coroutines.Dispatchers.IO)
}
