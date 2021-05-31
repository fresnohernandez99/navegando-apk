package cu.fresnohernandez99.app.navegando.data.model.responses

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource

class ErrorResponse : ResponseBody() {
    override fun contentLength(): Long = 0

    override fun contentType(): MediaType? = null

    override fun source(): BufferedSource = Buffer()
}