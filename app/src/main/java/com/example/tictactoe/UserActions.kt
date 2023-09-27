package com.example.tictactoe

//events
sealed class UserActions {
    data object PlayAgainButtonClicked: UserActions()
    data class BoardTapped(val cellNo: Int): UserActions()
}
