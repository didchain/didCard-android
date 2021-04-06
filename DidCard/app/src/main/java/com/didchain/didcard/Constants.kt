package com.didchain.didcard

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
object Constants {
    val BLOCK_TIME_OUT: Long = 40
    const val CODE_OPEN_ALBUM = 100
    const val CODE_OPEN_CAMERA = 101
    const val CODE_OPEN_GPS = 102
    const val CODE_WRITE_EXTERNAL_PERMISSION = 1
    const val CODE_LOCATION_PERMISSION = 2
    const val TIMEOUT = 4000

    const val KEY_OPEN_FINGERPRINT = "key_open_fingerprint"
    const val KEY_OPEN_NO_SECRET = "key_open_no_secret"
    const val KEY_ENCRYPTED_PASSWORD = "key_password"
    const val KEY_BIOMETRIC_PASSWORD = "key_biometric_password"
    const val KEY_BIOMETRIC_INITIALIZATION_VECTOR = "key_biometric_initialization_vector"
    const val KEY_DID_BIOMETRIC = "key_did_biometric"

    const val TAG_NAME = "DID"
}