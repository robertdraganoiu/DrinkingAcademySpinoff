package com.hack.drinkingacademy.game.domain.model

enum class GameElementType(val id: Long) {
    SINGLE(0),
    SINGLE_EXTEND(1),
    MULTI(2),
    MULTI_EXTEND(3);

    companion object {
        /** Used in game sqlite data source when querying the db */
        fun fromLong(id: Long) = GameElementType.values().first { it.id == id }

        /** Used in dbloader when adding from files*/
        fun fromName(name: String) = GameElementType.values().first { it.toString() == name }
    }
}