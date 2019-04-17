package com.vastausf.tasks.utils

import java.security.MessageDigest

fun String.getHashSHA256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(this.toByteArray())
    return digest.fold("") { str, it ->
        str + "%02x".format(it)
    }
}

fun String.trimAllSpaces(): String =
    Regex("\\s+").replace(this, " ").trim()