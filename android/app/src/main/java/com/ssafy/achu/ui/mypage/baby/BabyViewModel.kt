package com.ssafy.achu.ui.mypage.baby

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.util.CoilUtils.result
import com.ssafy.achu.core.ApplicationClass.Companion.babyRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.baby.BabyBirthRequest
import com.ssafy.achu.data.model.baby.BabyGenderRequest
import com.ssafy.achu.data.model.baby.BabyNicknameRequest
import com.ssafy.achu.data.model.baby.BabyRequest
import com.ssafy.achu.data.model.baby.BabyResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

private const val TAG = "BabyViewModel 안주현"

class BabyViewModel : ViewModel() {
    private val _babyUiState = MutableStateFlow(BabyUIState())
    val babyUiState: StateFlow<BabyUIState> = _babyUiState.asStateFlow()

    private val _isChanged = MutableSharedFlow<String>()
    val isChanged: SharedFlow<String> = _isChanged

    private val _isSelectedBabyChanged = MutableSharedFlow<Boolean>()
    val isSelectedBabyChanged: SharedFlow<Boolean> = _isSelectedBabyChanged



  fun updateBabyNickname(babyNicknameInput: String) {
        if (babyNicknameInput.length <= 6) {
            _babyUiState.value = _babyUiState.value.copy(
                babyNickname = babyNicknameInput
            )
        }else{
            updateToastString("닉네임은 6자 이하로 입력해주세요")
        }

    }

    fun updateResisterNickname(babyNicknameInput: String) {
        if (babyNicknameInput.length <= 6) {
            _babyUiState.value = _babyUiState.value.copy(
                resisterNickname = babyNicknameInput
            )
        }
    }

    fun updateBabyPhoto(babyPhoto: MultipartBody.Part) {
        _babyUiState.value = _babyUiState.value.copy(
            selectedPhoto = babyPhoto
        )
    }


    fun updateToastString(string: String){
        _babyUiState.value = _babyUiState.value.copy(
            toastString = string
        )
    }

    fun updateButtonAble(boolean: Boolean){
        _babyUiState.value = _babyUiState.value.copy(
            isButtonAble = boolean
        )
    }


    fun getBaby(babyId: Int) {
        viewModelScope.launch {
            babyRepository.getBaby(babyId).onSuccess {
                _babyUiState.value = _babyUiState.value.copy(
                    selectedBaby = it.data
                )
                Log.d(TAG, "getBaby: ${babyUiState.value.selectedBaby}")
                _isSelectedBabyChanged.emit(true)

                delay(1000)
                updateButtonAble(true)

            }.onFailure {

                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getBaby: ${errorResponse}")
                updateToastString(errorResponse.message)
                updateButtonAble(true)
                _isSelectedBabyChanged.emit(false)
            }
        }
    }

    fun registerBaby() {
        updateButtonAble(false)
        Log.d(TAG, "registerBaby: ${babyUiState.value.babyNickname}")
        viewModelScope.launch {
            Log.d(TAG, "registerBaby: $_babyUiState.value.selectedPhoto}")
            babyRepository.registerBaby(
                _babyUiState.value.selectedPhoto, BabyRequest(
                    nickname = babyUiState.value.resisterNickname,
                    birth = babyUiState.value.babyBirth,
                    gender = babyUiState.value.babyGender,
                )
            ).onSuccess {
                Log.d(TAG, "registerBaby: ${it.data}")
                getBaby(it.data.id)
                updateToastString("아이 등록 성공!")
                _isChanged.emit("등록")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "registerBaby: ${errorResponse}")
                updateToastString(errorResponse.message)
                _isChanged.emit("등록")
                updateButtonAble(true)
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
                updateToastString("닉네임 변경 성공!")
                _isChanged.emit("")
            }.onFailure {
                Log.d(TAG, "changeBabyNickname: ${it.message}")
                updateToastString("닉네임 변경 실패")
                _isChanged.emit("")
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
                getBaby(babyUiState.value.selectedBaby!!.id)
                Log.d(TAG, "changeBabyBirth: ${it}")
                updateToastString("생일 변경 성공!")
                _isChanged.emit("")

            }.onFailure {
                Log.d(TAG, "changeBabyBirth: ${it.message}")
                updateToastString("생일 변경 실패!")
                _isChanged.emit("")
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
            ).onSuccess {
                updateToastString("성별 변경 성공!")
                _isChanged.emit("")
            }.onFailure {
                updateToastString("성별 변경 실패")
                _isChanged.emit("")
            }
        }
    }

    fun updateBabyProfile(profileImage: MultipartBody.Part) {
        Log.d(TAG, "updateBabyProfile: ${profileImage}")
        viewModelScope.launch {
            babyRepository.updateProfileImage(
                babyUiState.value.selectedBaby!!.id,
                profileImage
            ).onSuccess { 
                getBaby(babyUiState.value.selectedBaby!!.id)
                updateToastString("프로필 변경 성공!")
                _isChanged.emit("")
            }.onFailure {
                Log.d(TAG, "updateBabyProfile: ${it.message}")
            }.onFailure{
                updateToastString("프로필 변경 실패")
                _isChanged.emit("")

                Log.d(TAG, "updateBabyProfile: ${it}")
            }
            
        }
    }

    fun deleteBaby(babyId: Int) {
        viewModelScope.launch {
            babyRepository.deleteBaby(babyId).onSuccess {
                Log.d(TAG, "deleteBaby: ${it.data}")
                updateToastString("아이정보 삭제 성공")
                _isChanged.emit("삭제")
            }.onFailure {
                Log.d(TAG, "deleteBaby: ${it.message}")
                updateToastString("아이정보 삭제 실패")
                _isChanged.emit("삭제")
            }

        }
    }


    fun confirmNickname(): Boolean {
        val state = _babyUiState.value
        val nicknameRegex = "^[가-힣A-Za-z]{2,6}$".toRegex()
        val isValid = nicknameRegex.matches(state.babyNickname)


        _babyUiState.update {
            it.copy(
                isCorrectNickname = isValid,
                babyNickname = if (!isValid) "" else it.babyNickname,
            )
        }
        return isValid
    }

    fun updateCorrectNickname(boolean: Boolean) {
        _babyUiState.update {
            it.copy(
                isCorrectNickname = boolean
            )
        }
    }

    fun updateBabyBirth(dateList: List<Int>) {
        _babyUiState.update {
            it.copy(
                babyBirth = dateList
            )
        }
    }

    fun updateBabyGender(string: String) {
        _babyUiState.update {
            it.copy(
                babyGender = string
            )
        }
    }



}