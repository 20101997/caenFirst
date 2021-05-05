package com.grupposts.data.models.request

import com.google.gson.annotations.SerializedName

data class DepartmentContainersRequest(
    @SerializedName("department_from")
    val departmentFrom: Int?,
    @SerializedName("department_to")
    val departmentTo: Int?
)