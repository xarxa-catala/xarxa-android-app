package cat.xarxacatalapp.core.downloads

import android.util.Log
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class OnAttachmentDownloadListener @Inject constructor() {
    var url: String? = null

    fun onAttachmentDownloadedSuccess() {

    }

    fun onAttachmentDownloadedError() {

    }

    fun onAttachmentDownloadedFinished() {

    }

    fun onAttachmentDownloadUpdate(percent: Int) {
        Log.e("ABDE", "$url progress percent is: $percent")
    }
}

@Singleton
class DownloadStateNotifier @Inject constructor(
    private val downloadListenerProvider: Provider<OnAttachmentDownloadListener>
) {

    private val downloadsMap = HashMap<String, OnAttachmentDownloadListener>()

    fun getAttachmentDownloadListener(url: String): OnAttachmentDownloadListener {
        val downloadListener = downloadListenerProvider.get().apply {
            this.url = url
        }

        downloadsMap[url] = downloadListener

        return downloadListener
    }
}
