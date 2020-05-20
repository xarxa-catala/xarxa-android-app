package cat.xarxacatalapp.core

data class CallResult<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): CallResult<T> {
            return CallResult(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): CallResult<T> {
            return CallResult(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): CallResult<T> {
            return CallResult(Status.LOADING, data, null)
        }
    }
}