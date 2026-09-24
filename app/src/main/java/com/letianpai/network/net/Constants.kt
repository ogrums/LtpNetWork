package com.letianpai.network.net

/**
 * Host of the local cloud mock (`go run ./mock` in third_party_demo).
 * Retrofit requires the trailing slash. Paths in NewApi start with `/`, so
 * they are resolved from this host root.
 *
 * Use 127.0.0.1 when the app and the mock run on the same machine.
 * Use 10.0.2.2 when the app runs in the Android emulator and the mock runs on the host.
 */
const val MOCK_HOST = "http://10.0.2.2:8080/"

/** Chinese region. Pointed at the mock until a production host is set. */
val RELEASE_HOST_TEST = MOCK_HOST

/** Non-Chinese region. Same mock, so a language change still hits it. */
val GLOBAL_IotUrl = MOCK_HOST
