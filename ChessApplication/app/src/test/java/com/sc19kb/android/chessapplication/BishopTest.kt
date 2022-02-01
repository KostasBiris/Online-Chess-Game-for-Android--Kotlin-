package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp

class BishopTest {

    @Test
    fun canMoveBishop_isCorrect() {
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.BISHOP, R.drawable.bishop_white))
        assertTrue(ChessBoardConsole.canMove(4, 4, 1, 7))
        assertTrue(ChessBoardConsole.canMove(4, 4, 6, 6))
        assertTrue(ChessBoardConsole.canMove(4, 4, 1, 1))
        assertTrue(ChessBoardConsole.canMove(4, 4, 6, 2))

        assertFalse(ChessBoardConsole.canMove(4, 4, 4, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 4, 3))
        assertFalse(ChessBoardConsole.canMove(4, 4, 1, 4))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 4))

        ChessBoardConsole.reset()
        assertFalse(ChessBoardConsole.canMove(5, 0, 2, 3))
        assertFalse(ChessBoardConsole.canMove(2, 0, 5, 3))
        assertFalse(ChessBoardConsole.canMove(2, 7, 4, 5))
        assertFalse(ChessBoardConsole.canMove(5, 7, 0, 3))
    }
}
