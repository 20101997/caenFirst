package com.grupposts.domain.usecases

import com.grupposts.domain.models.Container
import com.grupposts.domain.models.Product
import com.grupposts.domain.repositories.ContainerRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class AddProductsToContainerUseCase @Inject constructor(private val repository: ContainerRepository) {

    suspend operator fun invoke(
        travelId: Int?,
        containerId: Int?,
        products: List<Product>?
    ): Container {
        if (travelId == null ||
            containerId == null ||
            products == null
        ) {
            throw InvalidParamsException()
        } else {
            return repository.addProductsToContainer(
                travelId,
                containerId,
                products
            )
        }
    }

}