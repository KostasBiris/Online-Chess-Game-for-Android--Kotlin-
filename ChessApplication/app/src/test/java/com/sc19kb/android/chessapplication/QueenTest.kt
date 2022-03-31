package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test

class QueenTest {

    @Test
    fun canMoveQueen_isCorrect() {
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        assertTrue(ChessCore.canMove(4, 4, 1, 7))
        assertTrue(ChessCore.canMove(4, 4, 6, 6))
        assertTrue(ChessCore.canMove(4, 4, 1, 1))
        assertTrue(ChessCore.canMove(4, 4, 6, 2))

        assertTrue(ChessCore.canMove(4, 4, 4, 0))
        assertTrue(ChessCore.canMove(4, 4, 4, 6))
        assertTrue(ChessCore.canMove(4, 4, 1, 4))
        assertTrue(ChessCore.canMove(4, 4, 7, 4))

        assertFalse(ChessCore.canMove(4, 4, 2, 5))
        assertFalse(ChessCore.canMove(4, 4, 3, 6))
        assertFalse(ChessCore.canMove(4, 4, 5, 6))
        assertFalse(ChessCore.canMove(4, 4, 6, 5))
        assertFalse(ChessCore.canMove(4, 4, 2, 3))
        assertFalse(ChessCore.canMove(4, 4, 3, 2))
        assertFalse(ChessCore.canMove(4, 4, 6, 3))
        assertFalse(ChessCore.canMove(4, 4, 5, 2))

        ChessCore.reset()
        assertFalse(ChessCore.canMove(0, 3, 2, 3))
        assertFalse(ChessCore.canMove(0, 3, 5, 3))
        assertFalse(ChessCore.canMove(7, 3, 4, 5))
        assertFalse(ChessCore.canMove(7, 3, 0, 3))

        assertFalse(ChessCore.canMove(0, 3, 0, 3))
        assertFalse(ChessCore.canMove(0, 3, 5, 0))
        assertFalse(ChessCore.canMove(7, 3, 0, 5))
        assertFalse(ChessCore.canMove(7, 3, 7, 3))
    }
}
