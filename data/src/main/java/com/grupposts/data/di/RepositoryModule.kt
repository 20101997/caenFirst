package com.grupposts.data.di

import com.grupposts.data.repositories.*
import com.grupposts.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindContainerRepository(containerRepositoryImpl: ContainerRepositoryImpl): ContainerRepository

    @Binds
    abstract fun bindDepartmentRepository(departmentRepositoryImpl: DepartmentRepositoryImpl): DepartmentRepository

    @Binds
    abstract fun bindPianificationRepository(pianificationRepositoryImpl: PianificationRepositoryImpl): PianificationRepository

    @Binds
    abstract fun bindSignatureRepository(signatureRepositoryImpl: SignatureRepositoryImpl): SignatureRepository

    @Binds
    abstract fun bindTravelRepository(travelRepositoryImpl: TravelRepositoryImpl): TravelRepository

}