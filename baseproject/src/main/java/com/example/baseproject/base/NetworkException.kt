package com.example.baseproject.base

import java.io.IOException

data class NetworkException(val code: Int?, override val message: String?): IOException(message)
