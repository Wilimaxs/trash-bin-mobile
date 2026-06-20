package com.skripsi.myapplication.utils.extensions

import com.skripsi.myapplication.BuildConfig

fun String?.toFullImageUrl(): String? {
    if (this.isNullOrBlank()) return null
    if (this.startsWith("http", ignoreCase = true)) return this

    val base = BuildConfig.BASE_URL.trimEnd('/')
    val path = this.trimStart('/')
    return "$base/$path"
}