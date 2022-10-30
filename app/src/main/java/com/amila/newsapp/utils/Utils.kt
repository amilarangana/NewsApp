package com.amila.newsapp.utils

import java.math.BigInteger
import java.security.MessageDigest

class Utils {
    companion object{
        fun encryptString(input:String): String {
            val md = MessageDigest.getInstance("SHA-256")
            return BigInteger(1, md.digest(input.toByteArray()))
                .toString(16).padStart(32, '0')
        }
    }
}