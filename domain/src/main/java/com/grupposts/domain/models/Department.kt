package com.grupposts.domain.models

data class Department (
    val id : Int?,
    val name : String?,
    val referentName : String?,
    val referentPhone : String?,
    val floor : String?,
    val structureId : Int?,
    val showPosition : Int?,
    val productCategorise : List<String>?
)