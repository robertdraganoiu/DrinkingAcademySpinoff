package com.hack.drinkingacademy.user.domain.use_case

import com.hack.drinkingacademy.user.domain.model.UserTitle

class ConvertProgressToTitleUseCase {
    fun execute(progress: Long): UserTitle {
        return if (progress < 50)
            UserTitle.FRESHMAN
        else if (progress < 200)
            UserTitle.SOPHOMORE
        else if (progress < 500)
            UserTitle.JUNIOR
        else if (progress < 1000)
            UserTitle.SENIOR
        else if (progress < 5000)
            UserTitle.GRADUATE
        else if (progress < 100000)
            UserTitle.ASSISTANT
        else
            UserTitle.TEACHER
    }
}