package com.grupposts.data.api

import com.grupposts.data.models.DefaultResponse
import com.grupposts.data.models.request.*
import com.grupposts.data.models.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/token")
    suspend fun login(@Body request: TokenRequest): Response<TokenResponse>

    @POST("auth/token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<TokenResponse>

    @GET("auth/logout")
    suspend fun logout(): Response<DefaultResponse<Unit>>

    @GET("auth/user")
    suspend fun getUser(): Response<DefaultResponse<UserResponse>>

    @GET("pianifications")
    suspend fun getPianifications(): Response<DefaultResponse<List<PianificationResponse>>>

    @GET("pianifications/date/{date}")
    suspend fun getPianificationByDate(@Path("date") date: String): Response<DefaultResponse<PianificationResponse>>

    @GET("travel/{pianification_id}/create")
    suspend fun createTravel(@Path("pianification_id") pianificationId: Int): Response<DefaultResponse<TravelResponse>>

    @GET("travel/{travel_id}")
    suspend fun getTravel(@Path("travel_id") travelId: Int): Response<DefaultResponse<TravelResponse>>

    @POST("travel/{travel_id}/updatePosition")
    suspend fun updateTravelPosition(
        @Path("travel_id") travelId: Int,
        @Body request: TravelUpdatePositionRequest
    ): Response<DefaultResponse<TravelPositionResponse>>

    @GET("travel/{travel_id}/{journey_item_id}/start")
    suspend fun startJourney(
        @Path("travel_id") travelId: Int,
        @Path("journey_item_id") journeyItemId: Int
    ): Response<DefaultResponse<JourneyResponse>>

    @GET("travel/{travel_id}/{journey_item_id}/stop")
    suspend fun stopJourney(
        @Path("travel_id") travelId: Int,
        @Path("journey_item_id") journeyItemId: Int
    ): Response<DefaultResponse<JourneyResponse>>

    @GET("structures")
    suspend fun getStructures(): Response<List<StructureResponse>>

    @GET("products")
    suspend fun getProducts(): Response<List<ProductResponse>>

    @GET("products/department/{department_id}")
    suspend fun getDepartmentProducts(@Path("department_id") departmentId: Int): Response<DefaultResponse<List<ProductResponse>>>

    @GET("departments/{structure_id}")
    suspend fun getDepartments(@Path("structure_id") structureId: Int): Response<List<DepartmentResponse>>

    @POST("travel/{travel_id}/containers/create")
    suspend fun createContainer(
        @Path("travel_id") travelId: Int,
        @Body request: ContainersRequest
    ): Response<DefaultResponse<ContainerResponse>>

    @POST("travel/{travel_id}/containers/delete")
    suspend fun deleteContainers(
        @Path("travel_id") travelId: Int,
        @Body request: DeleteContainersRequest
    ): Response<DefaultResponse<Unit>>

    @POST("travel/{travel_id}/containers/{container_id}/edit")
    suspend fun editContainer(
        @Path("travel_id") travelId: Int,
        @Path("container_id") containerId: Int,
        @Body request: ContainersRequest
    ): Response<DefaultResponse<ContainerResponse>>

    @GET("travel/{travel_id}/containers")
    suspend fun getContainers(@Path("travel_id") travelId: Int): Response<DefaultResponse<List<ContainerResponse>>>

    @POST("travel/{travel_id}/containers/department")
    suspend fun getDepartmentContainers(
        @Path("travel_id") travelId: Int,
        @Body request: DepartmentContainersRequest
    ): Response<DefaultResponse<List<ContainerResponse>>>

    @GET("travel/{travel_id}/containers/{container_id}/delete")
    suspend fun deleteTravel(
        @Path("travel_id") travelId: Int,
        @Path("container_id") containerId: Int
    ): Response<DefaultResponse<Unit>>

    @POST("travel/{travel_id}/containers/{container_id}/addProducts")
    suspend fun addProductsToContainer(
        @Path("travel_id") travelId: Int,
        @Path("container_id") containerId: Int,
        @Body request: ContainersAddProductRequest
    ): Response<DefaultResponse<ContainerResponse>>

    @POST("travel/{travel_id}/containers/{container_id}/removeProduct")
    suspend fun removeProductsFromContainer(
        @Path("travel_id") travelId: Int,
        @Path("container_id") containerId: Int,
        @Body request: ContainersRemoveProductRequest
    ): Response<DefaultResponse<ContainerResponse>>

    @POST("travel/{travel_id}/containers/{container_id}/updateTemperature")
    suspend fun updateTemperature(
        @Path("travel_id") travelId: Int,
        @Path("container_id") containerId: Int,
        @Body request: ContainersUpdateTemperatureRequest
    ): Response<DefaultResponse<ContainerResponse>>

    @POST("signature/create/{journey_item_id}")
    suspend fun createSignature(
        @Path("journey_item_id") journeyItemId: Int,
        @Body request: SignatureCreateRequest
    ): Response<DefaultResponse<SignatureResponse>>

    @GET("signature/{signature_id}/delete")
    suspend fun deleteSignature(@Path("signature_id") signatureId: Int): Response<SignatureResponse>


}