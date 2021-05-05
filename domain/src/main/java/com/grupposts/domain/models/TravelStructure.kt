package com.grupposts.domain.models

data class TravelStructure(
    val id: Int?,
    val type : String?,
    val arrivalAt : String?,
    val structure : Structure?,
    val departments : List<Department>?
)
