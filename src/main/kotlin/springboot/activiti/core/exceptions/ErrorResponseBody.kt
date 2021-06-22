package springboot.activiti.core.exceptions

data class ErrorResponseBody(
    var errorCode: String,
    var errorMessage: String
)
