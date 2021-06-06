package cat.xarxacatalapp.core.downloads

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*


class ProgressResponseBody(
    private val responseBody: ResponseBody?,
    private val progressListener: OnAttachmentDownloadListener?
) : ResponseBody() {
    private var bufferedSource: BufferedSource? = null
    override fun contentType(): MediaType? {
        return responseBody?.contentType()
    }

    override fun contentLength(): Long {
        return responseBody?.contentLength() ?: 0L
    }

    override fun source(): BufferedSource {
        val responseBody = responseBody
        var bufferedSource = bufferedSource

        if (bufferedSource == null && responseBody != null) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource!!
    }


    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead = 0L

            override fun read(sink: Buffer?, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                val percent =
                    if (bytesRead == -1L) 100f else totalBytesRead.toFloat() / responseBody!!.contentLength()
                        .toFloat() * 100

                progressListener?.onAttachmentDownloadUpdate(percent.toInt())
                return bytesRead
            }
        }
    }
}