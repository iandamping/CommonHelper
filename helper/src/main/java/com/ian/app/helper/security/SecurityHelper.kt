package com.ian.app.helper.security

import android.util.Base64
import org.apache.commons.codec.binary.Hex
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SignatureException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


/**
 * Created by Ian Damping on 26,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
object SecurityHelper {

    private const val HMAC_SHA512 = "HmacSHA512"

    private fun toHexString(bytes: ByteArray): String {
        val formatter = Formatter()
        for (b in bytes) {
            formatter.format("%02x", b)
        }
        return formatter.toString()
    }

    @Throws(SignatureException::class, NoSuchAlgorithmException::class, InvalidKeyException::class)
    fun calculateHMACSHA512(data: String, key: String): String {
        val secretKeySpec = SecretKeySpec(key.toByteArray(), HMAC_SHA512)
        val mac: Mac = Mac.getInstance(HMAC_SHA512)
        mac.init(secretKeySpec)
        return toHexString(mac.doFinal(data.toByteArray()))
    }


    @Throws(
        UnsupportedEncodingException::class,
        NoSuchAlgorithmException::class,
        InvalidKeyException::class
    )
    fun computeHMACSHA512(value: String, securityHmac: String): String {
        val key = SecretKeySpec(securityHmac.toByteArray(), HMAC_SHA512)
        val mac: Mac = Mac.getInstance(HMAC_SHA512)
        mac.init(key)
        val bytes: ByteArray = mac.doFinal(value.toByteArray())

        return Hex.encodeHexString(bytes)
    }

    fun encodeToBase64(data: String): String =
        Base64.encodeToString(data.toByteArray(charset("UTF-8")), Base64.DEFAULT)

    fun decodeFromBase64(data: String): String {
        return Base64.decode(data, Base64.DEFAULT).toString(charset("UTF-8"))
    }

}