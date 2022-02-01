package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp

class KingTest {

    @Test
    fun canMoveKing_isCorrect() {
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
        assertTrue(ChessBoardConsole.canMove(4, 4, 3, 5))
        assertTrue(ChessBoardConsole.canMove(4, 4, 4, 5))
        assertTrue(ChessBoardConsole.canMove(4, 4, 5, 5))
        assertTrue(ChessBoardConsole.canMove(4, 4, 3, 4))
        assertTrue(ChessBoardConsole.canMove(4, 4, 5, 4))
        assertTrue(ChessBoardConsole.canMove(4, 4, 3, 3))
        assertTrue(ChessBoardConsole.canMove(4, 4, 4, 3))
        assertTrue(ChessBoardConsole.canMove(4, 4, 5, 3))

        assertFalse(ChessBoardConsole.canMove(4, 4, 1, 7))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 1, 1))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 2))
        assertFalse(ChessBoardConsole.canMove(4, 4, 4, 0))
        assertFalse(ChessBoardConsole.canMove(4, 4, 4, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 1, 4))
        assertFalse(ChessBoardConsole.canMove(4, 4, 7, 4))
        assertFalse(ChessBoardConsole.canMove(4, 4, 2, 5))
        assertFalse(ChessBoardConsole.canMove(4, 4, 3, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 5, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 5))
        assertFalse(ChessBoardConsole.canMove(4, 4, 2, 3))
        assertFalse(ChessBoardConsole.canMove(4, 4, 3, 2))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 3))
        assertFalse(ChessBoardConsole.canMove(4, 4, 5, 2))
    }

    @Test
    fun leftCastle_isCorrect() {
        TODO("Implement")
    }

    @Test
    fun rightCastle_isCorrect() {
        TODO("Implement")
    }
}
