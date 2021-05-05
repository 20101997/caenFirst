package com.grupposts.data.models.request

import com.google.gson.annotations.SerializedName

data class DeleteContainersRequest(
    @SerializedName("containers")
    val containers: List<Int>?
)
