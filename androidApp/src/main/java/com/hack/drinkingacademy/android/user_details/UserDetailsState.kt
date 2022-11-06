package com.hack.drinkingacademy.android.user_details

import com.hack.drinkingacademy.android.R
import com.hack.drinkingacademy.user.domain.model.UserTitle

data class UserDetailsState(
    val title: UserTitle = UserTitle.FRESHMAN,
    val avatarId: Int = R.drawable.freshman_avatar,
    val avatarDescriptionId: Int = R.string.freshman_avatar_description,
    val progress: Long = 0,
    val soundLevel: Long = 0,
    val musicLevel: Long = 0
)
