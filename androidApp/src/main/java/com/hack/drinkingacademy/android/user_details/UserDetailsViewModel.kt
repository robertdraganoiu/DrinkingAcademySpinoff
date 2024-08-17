package com.hack.drinkingacademy.android.user_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hack.drinkingacademy.android.R
import com.hack.drinkingacademy.user.domain.model.UserDetails
import com.hack.drinkingacademy.user.domain.model.UserTitle
import com.hack.drinkingacademy.user.domain.repository.UserDataSource
import com.hack.drinkingacademy.user.domain.use_case.ConvertProgressToTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userDataSource: UserDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val progressToTitle = ConvertProgressToTitleUseCase()
    private var userDetails: UserDetails? = null

    private val progress = savedStateHandle.getStateFlow("progress", 0L)
    private val soundLevel = savedStateHandle.getStateFlow("soundLevel", 0L)
    private val musicLevel = savedStateHandle.getStateFlow("musicLevel", 0L)

    val state = combine(
        progress,
        soundLevel,
        musicLevel
    ) { progress, soundLevel, musicLevel ->
        UserDetailsState(
            title = progressToTitle.execute(progress),
            avatarDescriptionId = getAvatarDescriptionFromTitle(progressToTitle.execute(progress)),
            progress = progress,
            soundLevel = soundLevel,
            musicLevel = musicLevel
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserDetailsState())

    init {
        loadUserDetails()
    }

    private fun loadUserDetails() {
        viewModelScope.launch {
            userDetails = userDataSource.getUser()
            savedStateHandle["progress"] = userDetails?.progress
            savedStateHandle["soundLevel"] = userDetails?.soundLevel
            savedStateHandle["musicLevel"] = userDetails?.musicLevel
        }
    }

    fun addProgress(added: Long) {
        if (added < 0) {
            Log.e(
                UserDetailsViewModel::class.simpleName,
                "Tried to add $added points. Aborted points update."
            )
            return
        }
        viewModelScope.launch {
            userDataSource.updateUserScore(progress.value + added)
            loadUserDetails()
        }
    }

    fun updateSoundLevel(sound: Long) {
        updateSoundAndMusicLevels(sound, userDetails?.musicLevel ?: 0)
    }

    fun updateMusicLevel(music: Long) {
        updateSoundAndMusicLevels(userDetails?.soundLevel ?: 0, music)
    }

    private fun updateSoundAndMusicLevels(sound: Long, music: Long) {
        if (sound < 0 || music < 0) return
        viewModelScope.launch {
            userDataSource.updateUserSettings(sound, music)
            loadUserDetails()
        }
    }

    private fun getAvatarDescriptionFromTitle(title: UserTitle): Int = when (title) {
        UserTitle.FRESHMAN -> R.string.user_avatar_freshman_description
        UserTitle.SOPHOMORE -> R.string.user_avatar_sophomore_description
        UserTitle.JUNIOR -> R.string.user_avatar_junior_description
        UserTitle.SENIOR -> R.string.user_avatar_senior_description
        UserTitle.GRADUATE -> R.string.user_avatar_graduate_description
        UserTitle.ASSISTANT -> R.string.user_avatar_assistant_description
        UserTitle.TEACHER -> R.string.user_avatar_teacher_description
    }
}