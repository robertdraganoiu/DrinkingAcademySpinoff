package com.hack.drinkingacademy.android.user_details

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
            avatarId = getAvatarIdFromTitle(progressToTitle.execute(progress)),
            avatarDescriptionId = getAvatarDescriptionFromTitle(progressToTitle.execute(progress)),
            progress = progress,
            soundLevel = soundLevel,
            musicLevel = musicLevel
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserDetailsState())

    init {
        loadUserDetails()
    }

    fun loadUserDetails() {
        userDetails = userDataSource.getUser()
        savedStateHandle["progress"] = userDetails?.progress
        savedStateHandle["soundLevel"] = userDetails?.soundLevel
        savedStateHandle["musicLevel"] = userDetails?.musicLevel
    }

    fun addProgress(added: Long) {
        if (added < 0) return
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

    private fun getAvatarIdFromTitle(title: UserTitle): Int {
        return when(title) {
            UserTitle.FRESHMAN -> R.drawable.freshman_avatar
            UserTitle.SOPHOMORE -> R.drawable.sophomore_avatar
            UserTitle.JUNIOR -> R.drawable.junior_avatar
            UserTitle.SENIOR -> R.drawable.senior_avatar
            UserTitle.GRADUATE -> R.drawable.graduate_avatar
            UserTitle.ASSISTANT -> R.drawable.assistant_avatar
            UserTitle.TEACHER -> R.drawable.teacher_avatar
        }
    }

    private fun getAvatarDescriptionFromTitle(title: UserTitle): Int {
        return when(title) {
            UserTitle.FRESHMAN -> R.string.freshman_avatar_description
            UserTitle.SOPHOMORE -> R.string.sophomore_avatar_description
            UserTitle.JUNIOR -> R.string.junior_avatar_description
            UserTitle.SENIOR -> R.string.senior_avatar_description
            UserTitle.GRADUATE -> R.string.graduate_avatar_description
            UserTitle.ASSISTANT -> R.string.assistant_avatar_description
            UserTitle.TEACHER -> R.string.teacher_avatar_description
        }
    }
}