package com.grupposts.data.models.request

import com.google.gson.annotations.SerializedName
import com.grupposts.data.models.ContainerModel

data class ContainersRequest(
    @SerializedName("container")
    val container: ContainerModel?
)
