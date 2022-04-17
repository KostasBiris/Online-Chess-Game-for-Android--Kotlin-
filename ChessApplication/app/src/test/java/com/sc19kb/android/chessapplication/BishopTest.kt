package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test

class BishopTest {

    @Test
    fun canMoveBishop_isCorrect() {
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.BISHOP, R.drawable.bishop_white))
        assertTrue(ChessCore.canMove(4, 4, 1, 7))
        assertTrue(ChessCore.canMove(4, 4, 6, 6))
        assertTrue(ChessCore.canMove(4, 4, 1, 1))
        assertTrue(ChessCore.canMove(4, 4, 6, 2))

        assertFalse(ChessCore.canMove(4, 4, 4, 6))
        assertFalse(ChessCore.canMove(4, 4, 4, 3))
        assertFalse(ChessCore.canMove(4, 4, 1, 4))
        assertFalse(ChessCore.canMove(4, 4, 6, 4))

        ChessCore.reset()
        assertFalse(ChessCore.canMove(5, 0, 2, 3))
        assertFalse(ChessCore.canMove(2, 0, 5, 3))
        assertFalse(ChessCore.canMove(2, 7, 4, 5))
        assertFalse(ChessCore.canMove(5, 7, 0, 3))
    }
}
