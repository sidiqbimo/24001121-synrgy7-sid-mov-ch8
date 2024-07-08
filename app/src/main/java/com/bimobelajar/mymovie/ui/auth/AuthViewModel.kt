package com.bimobelajar.mymovie.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bimobelajar.mymovie.MyApplication
import com.bimobelajar.mymovie.util.EspressoIdlingResource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStoreManager = MyApplication.dataStoreManager

    suspend fun login(email: String, password: String): Boolean {
        EspressoIdlingResource.increment() // "Espresso, tunggu!"
        val storedEmail = dataStoreManager.email.first()
        val storedPassword = dataStoreManager.password.first()
        val result = email == storedEmail && password == storedPassword
        EspressoIdlingResource.decrement() // "Espreso, the task is done!
        return result
    }

    fun register(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        EspressoIdlingResource.increment() // "Espresso, tunggu!"
        viewModelScope.launch {
            val storedEmail = dataStoreManager.email.first()
            if (storedEmail != null) {
                onError("User already registered")
            } else {
                dataStoreManager.saveEmail(email)
                dataStoreManager.savePassword(password)
                onSuccess()
            }
            EspressoIdlingResource.decrement() // "Espresso, the task is done!"
        }
    }
}
