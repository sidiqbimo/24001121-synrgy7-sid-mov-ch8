package com.bimobelajar.mymovie.ui.auth

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bimobelajar.core.data.DataStoreManager
import com.bimobelajar.mymovie.data.DataStoreManager
import com.bimobelajar.mymovie.ui.auth.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var authViewModel: AuthViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authViewModel = AuthViewModel(application).apply {
            dataStoreManager = this@AuthViewModelTest.dataStoreManager
        }
    }

    @Test
    fun loginSuccess() = runBlockingTest {
        `when`(dataStoreManager.email).thenReturn(flowOf("test@example.com"))
        `when`(dataStoreManager.password).thenReturn(flowOf("password"))

        val result = authViewModel.login("test@example.com", "password")
        Assert.assertTrue(result)
    }

    @Test
    fun loginFailure() = runBlockingTest {
        `when`(dataStoreManager.email).thenReturn(flowOf("test@example.com"))
        `when`(dataStoreManager.password).thenReturn(flowOf("password"))

        val result = authViewModel.login("wrong@example.com", "password")
        Assert.assertFalse(result)
    }
}
