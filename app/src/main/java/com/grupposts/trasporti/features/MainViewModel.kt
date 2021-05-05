package com.grupposts.trasporti.features

import androidx.lifecycle.LiveData
import com.grupposts.domain.models.*
import com.grupposts.domain.util.SingleLiveEvent
import com.grupposts.trasporti.R
import com.grupposts.trasporti.base.BaseViewModel
import com.grupposts.trasporti.base.ResourceProvider
import com.grupposts.trasporti.features.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    var selectedTravel: Travel? = null
    var selectedDepartment: Department? = null
    var selectedActionType: ActionType? = null
    var selectedJourney: Journey? = null

    var backPressedTime: Long = 0

    private val _exitApp = SingleLiveEvent<Unit>()
    val exitApp: LiveData<Unit> = _exitApp

    companion object {
        private const val DOUBLE_BACK_INTERVAL: Long = 2000
    }

    fun onBackPressed() {
        if (backPressedTime + DOUBLE_BACK_INTERVAL > System.currentTimeMillis()) {
            _exitApp.call()
        } else {
            showToast(resourceProvider.getString(R.string.press_to_exit_app))
            backPressedTime = System.currentTimeMillis()
        }
    }

}