package com.grupposts.domain.models

data class Journey(
    val id: Int?,
    val status : JourneyStatus?,
    val type : String?,
    val arrivalAt : String?,
    val structure : Structure?,
    val departments : List<Department>?
)
