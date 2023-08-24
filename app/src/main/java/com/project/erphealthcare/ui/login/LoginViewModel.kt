package com.project.erphealthcare.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.LoginResult

import com.project.erphealthcare.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val loginRepository: Repository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginLiveData = MutableLiveData<LoginResult>()
    val loginLiveData: LiveData<LoginResult> = _loginLiveData

    fun login(username: String, password: String) = runBlocking {
        launch {
            val res = loginRepository.login(username, password)
            _loginLiveData.value = res
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.contains('@')
    }
}