package com.example.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

   var state by mutableStateOf(GameState())

    val boardItems: MutableMap<Int, BoardCellValue> = mutableMapOf(
        1 to BoardCellValue.NONE,
        2 to BoardCellValue.NONE,
        3 to BoardCellValue.NONE,
        4 to BoardCellValue.NONE,
        5 to BoardCellValue.NONE,
        6 to BoardCellValue.NONE,
        7 to BoardCellValue.NONE,
        8 to BoardCellValue.NONE,
        9 to BoardCellValue.NONE
    )

    fun onAction(action: UserActions) {
        when(action) {
            is UserActions.BoardTapped -> {
                addValueToBoard(action.cellNo)
            }
            UserActions.PlayAgainButtonClicked -> {
                    gameReset()
            }
        }
    }

    private fun gameReset() {
        boardItems.forEach{ (i, _) ->
            boardItems[i] = BoardCellValue.NONE
        }
        state = state.copy(
            hintText = "Player 'O' turn",
            currentTurn = BoardCellValue.CIRCLE,
            victoryType = VictoryType.NONE,
            hasWon = false
        )
    }

    private fun addValueToBoard(cellNo: Int) {
        if (boardItems[cellNo] != BoardCellValue.NONE) {
            return
        }
        if (state.currentTurn == BoardCellValue.CIRCLE) {
            boardItems[cellNo] = BoardCellValue.CIRCLE
            if (checkForVictory(BoardCellValue.CIRCLE)) {
                state = state.copy(
                    hintText = "Player 'O' Won",
                    playerCircleCount = state.playerCircleCount + 1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )
            } else if (hasBoardFull()) {
                state = state.copy(
                    hintText = "Game Draw",
                    drawCount = state.drawCount + 1
                )
            } else {
                state = state.copy(
                    hintText = "Player 'X' turn",
                    currentTurn = BoardCellValue.CROSS
                )
            }
        } else if ( state.currentTurn == BoardCellValue.CROSS) {
            boardItems[cellNo] = BoardCellValue.CROSS
            if (checkForVictory(BoardCellValue.CROSS)) {
                state = state.copy(
                    hintText = "Player 'X' Won",
                    playerCrossCount = state.playerCrossCount + 1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )
            } else if (hasBoardFull()) {
                state = state.copy(
                    hintText = "Game Draw",
                    drawCount = state.drawCount + 1
                )
            } else {
                state = state.copy(
                    hintText = "Player 'O' turn",
                    currentTurn = BoardCellValue.CIRCLE
                )
            }
        }
//            if (hasBoardFull()){
//                state = state.copy(
//                    hintText = "Player 'O' Won",
//                    playerCircleCount = state.playerCircleCount + 1,
//                    currentTurn = BoardCellValue.NONE,
//                    hasWon = true
//                )
//            } else if(state.currentTurn == BoardCellValue.CROSS) {
//                boardItems[cellNo] = BoardCellValue.CROSS
//                state = state.copy(
//                    hintText = "Game Draw",
//                    drawCount = state.drawCount + 1
//                )
//            } else {
//                state = state.copy(
//                    hintText = "Player 'X' turn",
//                    currentTurn = BoardCellValue.CROSS
//                )
//            }
//
//        }else if ( state.currentTurn == BoardCellValue.CROSS ) {
//            boardItems[cellNo] = BoardCellValue.CROSS
//            state = if (checkForVictory(BoardCellValue.CROSS)) {
//                state.copy(
//                    hintText = "Player 'X' Won",
//                    playerCrossCount = state.playerCrossCount + 1,
//                    currentTurn = BoardCellValue.NONE,
//                    hasWon = true
//                )
//            } else {
//                state.copy(
//                    hintText = "Game Draw",
//                    drawCount = state.drawCount + 1
//                )
//            }
//        }
    }

    private fun checkForVictory(boardValue: BoardCellValue): Boolean {
        when {
            boardItems[1] == boardValue && boardItems[2] == boardValue && boardItems[3] == boardValue -> {
                state = state.copy(
                        victoryType = VictoryType.HORIZONTAL1
                )
                return true
            }
            boardItems[4] == boardValue && boardItems[5] == boardValue && boardItems[6] == boardValue -> {
                state = state.copy(
                    victoryType = VictoryType.HORIZONTAL2
                )
                return true
            }
            boardItems[7] == boardValue && boardItems[8] == boardValue && boardItems[9] == boardValue -> {
                state = state.copy(
                    victoryType = VictoryType.HORIZONTAL3
                )
                return true
            }
            boardItems[1] == boardValue && boardItems[4] == boardValue && boardItems[7] == boardValue -> {
                state = state.copy(
                    victoryType = VictoryType.VERTICAL1
                )
                return true
            }
            boardItems[2] == boardValue && boardItems[5] == boardValue && boardItems[8] == boardValue -> {
                state = state.copy(
                    victoryType = VictoryType.VERTICAL2
                )
                return true
            }
            boardItems[3] == boardValue && boardItems[6] == boardValue && boardItems[9] == boardValue -> {
                state = state.copy(
                    victoryType = VictoryType.VERTICAL3
                )
                return true
            }
            boardItems[1] == boardValue && boardItems[5] == boardValue && boardItems[9] == boardValue -> {
                state = state.copy(
                    victoryType = VictoryType.DIAGONAL1
                )
                return true
            }
            boardItems[3] == boardValue && boardItems[5] == boardValue && boardItems[7] == boardValue -> {
                state = state.copy(
                    victoryType = VictoryType.DIAGONAL2
                )
                return true
            }
            else -> return false
        }
    }

    private fun hasBoardFull() : Boolean {//testing if the board is full o completely filled
        if (boardItems.containsValue(BoardCellValue.NONE)) return false
        return true
    }
}