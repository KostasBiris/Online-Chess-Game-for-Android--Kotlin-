package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp

class QueenTest {

    @Test
    fun canMoveQueen_isCorrect() {
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        assertTrue(ChessBoardConsole.canMove(4, 4, 1, 7))
        assertTrue(ChessBoardConsole.canMove(4, 4, 6, 6))
        assertTrue(ChessBoardConsole.canMove(4, 4, 1, 1))
        assertTrue(ChessBoardConsole.canMove(4, 4, 6, 2))

        assertTrue(ChessBoardConsole.canMove(4, 4, 4, 0))
        assertTrue(ChessBoardConsole.canMove(4, 4, 4, 6))
        assertTrue(ChessBoardConsole.canMove(4, 4, 1, 4))
        assertTrue(ChessBoardConsole.canMove(4, 4, 7, 4))

        assertFalse(ChessBoardConsole.canMove(4, 4, 2, 5))
        assertFalse(ChessBoardConsole.canMove(4, 4, 3, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 5, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 5))
        assertFalse(ChessBoardConsole.canMove(4, 4, 2, 3))
        assertFalse(ChessBoardConsole.canMove(4, 4, 3, 2))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 3))
        assertFalse(ChessBoardConsole.canMove(4, 4, 5, 2))

        ChessBoardConsole.reset()
        assertFalse(ChessBoardConsole.canMove(0, 3, 2, 3))
        assertFalse(ChessBoardConsole.canMove(0, 3, 5, 3))
        assertFalse(ChessBoardConsole.canMove(7, 3, 4, 5))
        assertFalse(ChessBoardConsole.canMove(7, 3, 0, 3))

        assertFalse(ChessBoardConsole.canMove(0, 3, 0, 3))
        assertFalse(ChessBoardConsole.canMove(0, 3, 5, 0))
        assertFalse(ChessBoardConsole.canMove(7, 3, 0, 5))
        assertFalse(ChessBoardConsole.canMove(7, 3, 7, 3))
    }
}
