package com.hack.drinkingacademy.android.user_details

import com.hack.drinkingacademy.android.R
import com.hack.drinkingacademy.user.domain.model.UserTitle

data class UserDetailsState(
    val title: UserTitle = UserTitle.FRESHMAN,
    val avatarId: Int = R.drawable.user_avatar_freshman,
    val avatarDescriptionId: Int = R.string.user_avatar_freshman_description,
    val progress: Long = 0,
    val soundLevel: Long = 0,
    val musicLevel: Long = 0
)
