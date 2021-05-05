package com.grupposts.trasporti.features.container.addcontainer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grupposts.domain.models.Department
import com.grupposts.domain.models.Product
import com.grupposts.domain.models.Structure
import com.grupposts.domain.usecases.GetDepartmentsUseCase
import com.grupposts.domain.usecases.GetStructuresUseCase
import com.grupposts.domain.util.SingleLiveEvent
import com.grupposts.trasporti.R
import com.grupposts.trasporti.base.BaseViewModel
import com.grupposts.trasporti.base.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContainerViewModel @Inject constructor(
    private val getStructuresUseCase: GetStructuresUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _structures = MutableLiveData<List<Structure>>()
    val structures: LiveData<List<Structure>> = _structures

    private val _departments = MutableLiveData<List<Department>>()
    val departments: LiveData<List<Department>> = _departments

    private val _isTemperatureTrackingActive = MutableLiveData(true)
    val isTemperatureTrackingActive: LiveData<Boolean> = _isTemperatureTrackingActive

    private val _destinationStructureError = SingleLiveEvent<String?>()
    val destinationStructureError: LiveData<String?> = _destinationStructureError

    private val _destinationDepartmentError = SingleLiveEvent<String?>()
    val destinationDepartmentError: LiveData<String?> = _destinationDepartmentError

    private val _products = MutableLiveData<List<Product>?>()
    val products: LiveData<List<Product>?> = _products

    private var selectedStructure: Structure? = null
    private var selectedDepartment: Department? = null

    init {
        getStructure()
    }

    private fun getStructure() {
        viewModelScope.launch {
            val result = try {
                getStructuresUseCase()
            } catch (e: Exception) {
                setError(e)
                emptyList()
            }
            _structures.postValue(result)
        }
    }

    private fun getDepartments(structureId: Int?) {
        viewModelScope.launch {
            setLoading(true)
            val result = try {
                getDepartmentsUseCase(structureId)
            } catch (e: Exception) {
                setError(e)
                emptyList()
            }

            _departments.postValue(result)
            setLoading(false)
        }
    }

    fun setTemperatureTracking(isActive: Boolean) {
        _isTemperatureTrackingActive.postValue(isActive)
    }

    fun onDestinationStructureSelected(structure: Structure) {
        selectedStructure = structure
        _destinationStructureError.postValue(null)
        getDepartments(structure.id)
    }

    fun onDestinationDepartmentSelected(department: Department) {
        selectedDepartment = department
        _destinationDepartmentError.postValue(null)
    }

    fun areDestinationFieldFilled(): Boolean {
        return if (selectedStructure?.name.isNullOrEmpty() ||
            selectedStructure?.name == resourceProvider.getString(R.string.select_structure_destination)
        ) {
            _destinationStructureError.postValue(resourceProvider.getString(R.string.select_structure_to_continue))
            false
        } else if (selectedDepartment?.name.isNullOrEmpty() ||
            selectedDepartment?.name == resourceProvider.getString(R.string.select_department_destination)
        ) {
            _destinationDepartmentError.postValue(resourceProvider.getString(R.string.select_department_to_continue))
            false
        } else {
            true
        }
    }

    fun setProducts(products: List<Product>?) {
        _products.postValue(products)
    }

    fun deleteProduct(product: Product) {
        val list = products.value?.toMutableList()
        list?.remove(product)
        _products.postValue(list)
    }

}