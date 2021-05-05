package com.grupposts.trasporti.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grupposts.domain.usecases.LoginUseCase
import com.grupposts.domain.util.EmptyPasswordException
import com.grupposts.domain.util.InvalidEmailException
import com.grupposts.domain.util.SingleLiveEvent
import com.grupposts.trasporti.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _isLoginButtonEnabled = MutableLiveData<Boolean>()
    val isLoginButtonEnabled: LiveData<Boolean> = _isLoginButtonEnabled

    private val _usernameError = MutableLiveData<String?>()
    val usernameError: LiveData<String?> = _usernameError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _navigateToHome = SingleLiveEvent<Unit>()
    val navigateToHome: LiveData<Unit> = _navigateToHome

    fun login(username: String?, password: String?) {
        viewModelScope.launch {
            try {
                setLoading(true)
                _isLoginButtonEnabled.value = false
                _usernameError.value = null
                _passwordError.value = null
                loginUseCase(username, password)
                _navigateToHome.call()
            } catch (e: InvalidEmailException) {
                _usernameError.value = e.message
            } catch (e: EmptyPasswordException) {
                _passwordError.value = e.message
            } catch (e: Exception) {
                setError(e)
            } finally {
                _isLoginButtonEnabled.value = true
                setLoading(false)
            }
        }
    }

}