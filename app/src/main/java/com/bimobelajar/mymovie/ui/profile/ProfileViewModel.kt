package com.bimobelajar.mymovie.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bimobelajar.mymovie.MyApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStoreManager = MyApplication.dataStoreManager

    private val _userName = MutableLiveData<String?>()
    val userName: LiveData<String?> = _userName

    private val _fullName = MutableLiveData<String?>()
    val fullName: LiveData<String?> = _fullName

    private val _birthdate = MutableLiveData<String?>()
    val birthdate: LiveData<String?> = _birthdate

    private val _address = MutableLiveData<String?>()
    val address: LiveData<String?> = _address

    init {
        viewModelScope.launch {
            _userName.value = dataStoreManager.username.first()
            _fullName.value = dataStoreManager.fullName.first()
            _birthdate.value = dataStoreManager.birthdate.first()
            _address.value = dataStoreManager.address.first()
        }
    }

    fun setUserName(userName: String) {
        viewModelScope.launch {
            dataStoreManager.saveUsername(userName)
            _userName.value = userName
        }
    }

    fun setFullName(fullName: String) {
        viewModelScope.launch {
            dataStoreManager.saveFullName(fullName)
            _fullName.value = fullName
        }
    }

    fun setBirthdate(birthdate: String) {
        viewModelScope.launch {
            dataStoreManager.saveBirthdate(birthdate)
            _birthdate.value = birthdate
        }
    }

    fun setAddress(address: String) {
        viewModelScope.launch {
            dataStoreManager.saveAddress(address)
            _address.value = address
        }
    }

    fun saveChanges(username: String, fullName: String, birthdate: String, address: String) {
        viewModelScope.launch {
            dataStoreManager.saveUsername(username)
            dataStoreManager.saveFullName(fullName)
            dataStoreManager.saveBirthdate(birthdate)
            dataStoreManager.saveAddress(address)
            _userName.value = username
            _fullName.value = fullName
            _birthdate.value = birthdate
            _address.value = address
        }
    }
}
