package cat.xarxacatalapp.core

import java.util.concurrent.TimeUnit

/**
 * This class contains some constant data used in App.
 */
object AppConstants {
    const val XARXA_CATALA_SERVCIE_DOMAIN = "https://api.multimedia.xarxacatala.cat"
    const val CONNECTION__CONNECT_TIMEOUT = 30000
    const val CONNECTION__READ_TIMEOUT = 10000
    const val CONNECTION__WRITE_TIMEOUT = 10000
    val CONNECTION__TIMEOUT_TIME_UNIT = TimeUnit.MILLISECONDS
}

