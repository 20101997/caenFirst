package com.grupposts.domain.util

const val INVALID_PARAM_EXCEPTION = "Invalid request parameters"

const val EMPTY_BODY_MESSAGE = "Response body is empty or null"

const val INVALID_DATA_MESSAGE = "Received data is null"

const val GENERIC_NETWORK_ERROR_MESSAGE = "Unknown network error"

const val GENERIC_SERVER_ERROR_MESSAGE = "Unknown server error"

const val INVALID_EMAIL_ERROR = "Invalid email address entered"

const val EMPTY_PASSWORD_ERROR = "Empty password entered"

class InvalidParamsException : Exception(INVALID_PARAM_EXCEPTION)

class EmptyBodyException : Exception(EMPTY_BODY_MESSAGE)

class InvalidDataException : Exception(INVALID_DATA_MESSAGE)

class GenericNetworkException(message: String?) :
    Exception(message ?: GENERIC_NETWORK_ERROR_MESSAGE)

class ServerErrorException(message: String?) : Exception(message ?: GENERIC_SERVER_ERROR_MESSAGE)

class InvalidEmailException : Exception(INVALID_EMAIL_ERROR)

class EmptyPasswordException : Exception(EMPTY_PASSWORD_ERROR)