package com.grupposts.domain.models

data class Travel(
    val id : Int?,
    val date : String?,
    val vehicle : Vehicle?,
    val journeys : List<Journey>?
)
