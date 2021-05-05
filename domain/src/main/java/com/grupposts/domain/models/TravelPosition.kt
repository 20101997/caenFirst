package com.grupposts.domain.models

data class TravelPosition(
    val id : Int?,
    val date : String?,
    val travelId : Int?,
    val vehicleId : Int?,
    val userId : Int?,
    val isActive : Int?,
    val position : List<String>?
)
