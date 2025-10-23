package com.calyrsoft.ucbp1.features.dollar.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DollarViewModel(
    val fetchDollarUseCase: FetchDollarUseCase)
    : ViewModel() {
        sealed class DollarUIState {
            object Loading: DollarUIState()
            class Error(val message: String): DollarUIState()
            class Success(val data: DollarModel): DollarUIState()
        }
        init {
            getDollar()

        }

        private val _uiState = MutableStateFlow<DollarUIState>(DollarUIState.Loading)
        val uiState: StateFlow<DollarUIState> = _uiState.asStateFlow()
        fun getDollar() {
            viewModelScope.launch(Dispatchers.IO) {
                getToken()
                fetchDollarUseCase.invoke().collect {
                    data -> _uiState.value = DollarUIState.Success(data)
                }
            }
        }

    suspend fun getToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                if (token != null) {
                    Log.d("FIREBASE", "FCM TOKEN: $token")
                    continuation.resume(token)
                } else {

                    Log.w("FIREBASE", "Token is null even though task was successful")
                    continuation.resumeWithException(Exception("FCM token was null"))
                }
            } else {

                Log.w("FIREBASE", "Fetching FCM token failed", task.exception)
                continuation.resumeWithException(task.exception ?: Exception("Error getting FCM token"))
            }
        }
    }

    }