package com.ssafy.achu.ui.mypage.baby

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.babyRepository
import com.ssafy.achu.core.ApplicationClass.Companion.userRepository
import com.ssafy.achu.data.model.baby.BabyBirthRequest
import com.ssafy.achu.data.model.baby.BabyGenderRequest
import com.ssafy.achu.data.model.baby.BabyNicknameRequest
import com.ssafy.achu.data.model.baby.BabyRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

private const val TAG = "BabyViewModel 안주현"

class BabyViewModel : ViewModel() {
    private val _babyUiState = MutableStateFlow(BabyUIState())
    val babyUiState: StateFlow<BabyUIState> = _babyUiState.asStateFlow()

    fun updateBabyNickname(babyNicknameInput: String) {
        _babyUiState.value = _babyUiState.value.copy(
            babyNickname = babyNicknameInput
        )
    }


    fun getBaby(babyId: Int) {
        viewModelScope.launch {
            babyRepository.getBaby(babyId).onSuccess {
                _babyUiState.value = _babyUiState.value.copy(
                    selectedBaby = it.data
                )
                Log.d(TAG, "getBaby: ${babyUiState.value.selectedBaby}")
            }.onFailure {
                Log.d(TAG, "getBaby: ${it.message}")
            }
        }
    }

    fun registerBaby(profileImage: MultipartBody.Part?) {
        viewModelScope.launch {
            babyRepository.registerBaby(
                profileImage, BabyRequest(
                    nickname = babyUiState.value.babyNickname,
                    birth = babyUiState.value.babyBirth,
                    gender = babyUiState.value.babyGender,
                )
            ).onSuccess {
                Log.d(TAG, "registerBaby: ${it.data}")
            }.onFailure {
                Log.d(TAG, "registerBaby: ${it.message}")
            }
        }
    }

    fun changeBabyNickname() {
        viewModelScope.launch {
            babyRepository.updateNickname(
                babyUiState.value.selectedBaby!!.id,
                BabyNicknameRequest(
                    nickname = babyUiState.value.babyNickname,
                )
            ).onSuccess {
                getBaby(babyUiState.value.selectedBaby!!.id)
            }.onFailure {
                Log.d(TAG, "changeBabyNickname: ${it.message}")
            }

        }
    }

    fun changeBabyBirth() {
        viewModelScope.launch {
            babyRepository.updateBirth(
                babyUiState.value.selectedBaby!!.id,
                BabyBirthRequest(
                    birth = babyUiState.value.babyBirth,
                )
            ).onSuccess {

                Log.d(TAG, "changeBabyBirth: ${it}")
            }.onFailure {

                Log.d(TAG, "changeBabyBirth: ${it.message}")
            }
        }

    }


    fun changeBabyGender() {
        viewModelScope.launch {
            babyRepository.updateGender(
                babyUiState.value.selectedBaby!!.id,
                BabyGenderRequest(
                    gender = babyUiState.value.babyGender,
                )
            )
        }
    }

    fun updateBabyProfile(profileImage: MultipartBody.Part) {
        viewModelScope.launch {
            babyRepository.updateProfileImage(
                babyUiState.value.selectedBaby!!.id,
                profileImage
            )
        }
    }

    fun deleteBaby(babyId: Int) {
        viewModelScope.launch {
            babyRepository.deleteBaby(babyId).onSuccess {
                Log.d(TAG, "deleteBaby: ${it.data}")
            }.onFailure {
                Log.d(TAG, "deleteBaby: ${it.message}")
            }

        }
    }


    fun confirmNickname() {
        val state = _babyUiState.value
        val nicknameRegex = "^[가-힣A-Za-z]{2,6}$".toRegex()
        val isValid = nicknameRegex.matches(state.babyNickname)


        _babyUiState.update {
            it.copy(
                isCorrectNickname = isValid,
                babyNickname = if (!isValid) "" else it.babyNickname,

                )
        }


    }

    fun updateCorrectNickname(boolean: Boolean) {
        _babyUiState.update {
            it.copy(
                isCorrectNickname = boolean
            )
        }
    }

}