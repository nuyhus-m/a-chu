package com.ssafy.achu.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.babyRepository
import com.ssafy.achu.core.ApplicationClass.Companion.userRepository
import com.ssafy.achu.data.model.baby.BabyResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


private const val TAG = "ActivityViewModel_안주현"

class ActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ActivityUIState())
    val uiState: StateFlow<ActivityUIState> = _uiState.asStateFlow()

    fun updateSelectedBaby(baby: BabyResponse) {
        _uiState.update {
            it.copy(
                selectedBaby = baby
            )
        }
    }

    fun getUserinfo() {
        viewModelScope.launch {
            userRepository.getMyInfo()
                .onSuccess {
                    if (it.result == "SUCCESS") {
                        val userData = it.data
                        _uiState.update {
                            it.copy(
                                user = userData
                            )
                        }
                        Log.d(TAG, "getUserinfo: ${it}")
                    }
                }.onFailure {
                    Log.d(TAG, "getUserinfo: ${it.message}")
                }
        }
    }


    fun getBabyList() {
        viewModelScope.launch {
            babyRepository.getBabyList().onSuccess {
                _uiState.update {
                    it.copy(
                        babyList = it.babyList
                    )
                }
            }.onFailure {
                Log.d(TAG, "getBabyList: ${it.message}")
            }
        }
    }


}