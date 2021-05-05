package com.grupposts.domain.models

data class Pianification (
    val id : Int?,
    val date : String?,
    val vehicle : List<Vehicle>?,
    val travelStructures : List<TravelStructure>?
)