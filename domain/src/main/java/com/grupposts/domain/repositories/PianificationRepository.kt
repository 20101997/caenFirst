package com.grupposts.domain.repositories

import com.grupposts.domain.models.Pianification

interface PianificationRepository {
    suspend fun getPianifications(): List<Pianification>
    suspend fun getPianificationByDate(date:String) : Pianification
}