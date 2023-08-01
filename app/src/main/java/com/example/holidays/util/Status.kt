package com.example.holidays.util

import androidx.annotation.StringRes
import com.example.holidays.R

enum class Status(val value: Int, @StringRes val message: Int) {
    OK(200, R.string.status_ok),
    ERROR_PARAMS(400, R.string.status_error_params),
    ERROR_UNAUTHORIZED(401, R.string.status_error_unauthorized),
    ERROR_PAYMENT(402, R.string.status_error_payment),
    ERROR_INSECURE(403, R.string.status_error_insecure),
    ERROR_LIMIT(429, R.string.status_error_monthly_limit),
    ERROR_SERVER(500, R.string.status_error_server);

    companion object {
        fun byStatusCode(code: Int): Status? = values().find { it.value == code }
    }
}