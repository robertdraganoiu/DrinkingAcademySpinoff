package com.hack.drinkingacademy.user.domain.use_case

class ConvertProgressToTitleUseCase {
    fun execute(progress: Long): String {
        // TODO do actual mapping
        if (progress == 0L)
            return "Freshman"
        else if (progress == 1L)
            return "Sophomore"
        else if (progress == 2L)
            return "Junior"
        else (progress == 1L)
            return "Senior"
    }
}