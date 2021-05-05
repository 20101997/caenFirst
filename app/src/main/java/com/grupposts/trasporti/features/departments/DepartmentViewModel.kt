package com.grupposts.trasporti.features.departments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grupposts.domain.models.Department
import com.grupposts.domain.usecases.GetDepartmentsUseCase
import com.grupposts.trasporti.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DepartmentViewModel @Inject constructor(
    private val getDepartmentsUseCase: GetDepartmentsUseCase
) : BaseViewModel() {

    private val _departments = MutableLiveData<List<Department>>()
    val departments: LiveData<List<Department>> = _departments

    fun getDepartments(structureId: Int?) {
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
}