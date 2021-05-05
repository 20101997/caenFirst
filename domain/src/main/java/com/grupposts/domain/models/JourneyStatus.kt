package com.grupposts.domain.models

enum class JourneyStatus(val value: String) {
    PENDING("pending"),
    TRAVELLING("travelling"),
    COMPLETED("completed"),
    UNKNOWN("unknown")
}