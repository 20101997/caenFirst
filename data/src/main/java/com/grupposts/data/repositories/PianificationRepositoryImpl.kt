package com.grupposts.data.repositories

import com.grupposts.data.api.ApiService
import com.grupposts.data.mappers.PianificationMapper
import com.grupposts.data.utils.handleDefaultResponse
import com.grupposts.domain.models.Pianification
import com.grupposts.domain.repositories.PianificationRepository
import javax.inject.Inject

class PianificationRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val mapper: PianificationMapper
) : PianificationRepository {

    override suspend fun getPianifications(): List<Pianification> {
        val result = api.getPianifications().handleDefaultResponse()
        return result.map { mapper.toPianification(it) }
    }

    override suspend fun getPianificationByDate(date: String): Pianification {
        val result = api.getPianificationByDate(date).handleDefaultResponse()
        return mapper.toPianification(result)
    }

}