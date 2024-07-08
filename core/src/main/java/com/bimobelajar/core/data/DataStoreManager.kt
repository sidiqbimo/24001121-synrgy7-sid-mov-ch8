package com.bimobelajar.core.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager private constructor(private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: DataStoreManager? = null

        fun getInstance(context: Context): DataStoreManager {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStoreManager(context)
                INSTANCE = instance
                instance
            }
        }
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    private val EMAIL_KEY = stringPreferencesKey("email")
    private val PASSWORD_KEY = stringPreferencesKey("password")
    private val USERNAME_KEY = stringPreferencesKey("username")
    private val FULL_NAME_KEY = stringPreferencesKey("full_name")
    private val BIRTHDATE_KEY = stringPreferencesKey("birthdate")
    private val ADDRESS_KEY = stringPreferencesKey("address")

    val email: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[EMAIL_KEY]
    }

    val password: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PASSWORD_KEY]
    }

    val username: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USERNAME_KEY]
    }

    val fullName: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[FULL_NAME_KEY]
    }

    val birthdate: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[BIRTHDATE_KEY]
    }

    val address: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[ADDRESS_KEY]
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
        }
    }

    suspend fun savePassword(password: String) {
        context.dataStore.edit { prefs ->
            prefs[PASSWORD_KEY] = password
        }
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
        }
    }

    suspend fun saveFullName(fullName: String) {
        context.dataStore.edit { prefs ->
            prefs[FULL_NAME_KEY] = fullName
        }
    }

    suspend fun saveBirthdate(birthdate: String) {
        context.dataStore.edit { prefs ->
            prefs[BIRTHDATE_KEY] = birthdate
        }
    }

    suspend fun saveAddress(address: String) {
        context.dataStore.edit { prefs ->
            prefs[ADDRESS_KEY] = address
        }
    }
}
