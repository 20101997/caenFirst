package com.grupposts.trasporti.features.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.grupposts.data.utils.SessionManager
import com.grupposts.domain.models.*
import com.grupposts.domain.usecases.*
import com.grupposts.domain.util.SingleLiveEvent
import com.grupposts.trasporti.R
import com.grupposts.trasporti.base.BaseViewModel
import com.grupposts.trasporti.utils.greaterThan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val logoutUseCase: LogoutUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getPianificationByDateUseCase: GetPianificationByDateUseCase,
    private val createTravelUseCase: CreateTravelUseCase,
    private val startJourneyUseCase: StartJourneyUseCase,
    private val stopJourneyUseCase: StopJourneyUseCase
) : BaseViewModel() {

    private val _navigateToLogin = SingleLiveEvent<Unit>()
    val navigateToLogin: LiveData<Unit> = _navigateToLogin

    private val _navigateToDepartments = SingleLiveEvent<Unit>()
    val navigateToDepartments: LiveData<Unit> = _navigateToDepartments

    private val _pianification = MutableLiveData<Pianification?>()
    val pianification: LiveData<Pianification?> = _pianification

    private val _noTravelFoundVisibility = SingleLiveEvent<Int>()
    val noTravelFoundVisibility: LiveData<Int> = _noTravelFoundVisibility

    private val _toolbarDate = MutableLiveData<String>()
    val toolbarDate: LiveData<String> = _toolbarDate

    private val _toolbarDateColor = MutableLiveData<Int>()
    val toolbarDateColor: LiveData<Int> = _toolbarDateColor

    private val _isJourneyAdapter = MutableLiveData(true)
    val isJourneyAdapter: LiveData<Boolean> = _isJourneyAdapter

    private val dateFormatter = DateTimeFormatter.ofPattern("EEE\ndd/MM/yyyy", Locale.ITALY)
    private val todayDate = LocalDate.now()

    private val _travel = MutableLiveData<Travel?>()
    val travel: LiveData<Travel?> = _travel

    private val _startedJourney = MutableLiveData<Journey?>()
    val startedJourney: LiveData<Journey?> = _startedJourney

    private val _lastLocation = MutableLiveData<Location?>()
    val lastLocation: LiveData<Location?> = _lastLocation

    private val _destinationLocation = MutableLiveData<Location?>()
    val destinationLocation: LiveData<Location?> = _destinationLocation

    private val selectedDate = MutableLiveData(todayDate)

    var isJourneyStarted: Boolean = false

    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = TimeUnit.SECONDS.toMillis(30)
        fastestInterval = TimeUnit.SECONDS.toMillis(15)
        maxWaitTime = TimeUnit.MINUTES.toMillis(1)
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            _lastLocation.postValue(locationResult.lastLocation)
        }
    }

    companion object {
        private const val ONE_WEEK_IN_SECONDS = 604800
    }

    fun checkToken() {
        val token = sessionManager.token
        if (token?.accessToken == null) {
            _navigateToLogin.call()
        } else if (token.expiresIn != null && token.expiresIn in 0..ONE_WEEK_IN_SECONDS) {
            refreshToken(token.refreshToken)
        } else {
            // if token is ok, get today's pianification
            getPianification(todayDate.toString())
        }
    }

    private fun refreshToken(token: String?) {
        viewModelScope.launch {
            setLoading(true)
            try {
                refreshTokenUseCase(token)
                // if token is refreshed correctly, check it again
                checkToken()
            } catch (e: Exception) {
                setError(e)
            }
            setLoading(false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            setLoading(true)
            try {
                logoutUseCase()
            } catch (e: Exception) {
                setError(e)
            }
            setLoading(false)
            _navigateToLogin.call()
        }
    }

    private fun getPianification(date: String?) {
        viewModelScope.launch {
            setLoading(true)
            val result = try {
                getPianificationByDateUseCase(date)
            } catch (e: Exception) {
                setError(e)
                null
            }

            if (date == todayDate.toString()) {
                createTravel(result?.id)
            }

            _pianification.postValue(result)
            setLoading(false)
        }
    }

    fun formatArrivalTime(arrivalTime: String?): String = arrivalTime?.let {
        LocalTime.parse(it).toString().replace(":", ".")
    } ?: ""

    fun formatDepartments(departments: List<Department>?): String =
        departments?.map { it.name }?.joinToString("\n\n") ?: ""

    fun getTodayDate(): String = todayDate.format(dateFormatter)

    fun increaseToolbarDate() {
        selectedDate.value = selectedDate.value?.plusDays(1)
        val formattedDate = selectedDate.value?.format(dateFormatter) ?: ""
        _toolbarDate.postValue(formattedDate)

        checkToolbarDate()
    }

    fun decreaseToolbarDate() {
        if (selectedDate.value.greaterThan(LocalDate.now())) {
            selectedDate.value = selectedDate.value?.minusDays(1)
            val formattedDate = selectedDate.value?.format(dateFormatter) ?: ""
            _toolbarDate.postValue(formattedDate)

            checkToolbarDate()
        }
    }

    private fun checkToolbarDate() {
        if (selectedDate.value != todayDate) {
            _toolbarDateColor.postValue(R.color.white60)
            _isJourneyAdapter.postValue(false)
            getPianification(selectedDate.value.toString())
        } else {
            _toolbarDateColor.postValue(R.color.white)
            _isJourneyAdapter.postValue(true)
        }
    }

    private fun createTravel(pianificationId: Int?) {
        viewModelScope.launch {
            setLoading(true)
            val result = try {
                createTravelUseCase(pianificationId)
            } catch (e: Exception) {
                setError(e)
                null
            }

            result?.let {
                if (it.journeys.isNullOrEmpty()) {
                    _noTravelFoundVisibility.postValue(View.VISIBLE)
                } else {
                    _noTravelFoundVisibility.postValue(View.GONE)
                }
            }

            _travel.postValue(result)
            setLoading(false)
        }
    }

    fun startJourney(journeyId: Int?) {
        viewModelScope.launch {
            setLoading(true)
            val result = try {
                startJourneyUseCase(travel.value?.id, journeyId)
            } catch (e: Exception) {
                setError(e)
                null
            }

            result?.let { isJourneyStarted = true }

            _startedJourney.postValue(result)
            setLoading(false)
        }
    }

    fun stopJourney(journeyId: Int?) {
        viewModelScope.launch {
            setLoading(true)
            try {
                stopJourneyUseCase(travel.value?.id, journeyId)
                isJourneyStarted = false
                _navigateToDepartments.call()
            } catch (e: Exception) {
                setError(e)
            }
            setLoading(false)
        }
    }

    fun getStructureLocation(context: Context, structure: Structure?) {
        viewModelScope.launch {
            val location = try {
                if (structure == null) {
                    throw Exception("Invalid structure")
                }
                val locationName: String = structure.run { "$address, $city, $cap, $province" }
                val addresses = withContext(Dispatchers.IO) {
                    Geocoder(context).getFromLocationName(
                        locationName,
                        1
                    )
                }
                Location(LocationManager.GPS_PROVIDER).apply {
                    latitude = addresses[0].latitude
                    longitude = addresses[0].longitude
                }
            } catch (e: Exception) {
                null
            }

            _destinationLocation.postValue(location)
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(fusedLocationClient: FusedLocationProviderClient) {
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun stopLocationUpdates(fusedLocationClient: FusedLocationProviderClient) {
        try {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun isCloseToDestination(location: Location?): Boolean {
        destinationLocation.value?.let { destination ->
            return location?.let {
                destination.distanceTo(it) < 1000
            } ?: false
        }

        return false
    }

}