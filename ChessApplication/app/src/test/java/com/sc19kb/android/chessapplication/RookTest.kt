package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test

class RookTest {

    @Test
    fun canMoveRook_isCorrect() {
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))
        assertTrue(ChessCore.canMove(4, 4, 4, 0))
        assertTrue(ChessCore.canMove(4, 4, 4, 6))
        assertTrue(ChessCore.canMove(4, 4, 1, 4))
        assertTrue(ChessCore.canMove(4, 4, 7, 4))

        assertFalse(ChessCore.canMove(4, 4, 3, 2))
        assertFalse(ChessCore.canMove(4, 4, 5, 5))
        assertFalse(ChessCore.canMove(4, 4, 1, 3))
        assertFalse(ChessCore.canMove(4, 4, 7, 0))

        ChessCore.reset()
        assertFalse(ChessCore.canMove(0, 7, 0, 3))
        assertFalse(ChessCore.canMove(0, 0, 5, 0))
        assertFalse(ChessCore.canMove(7, 0, 0, 5))
        assertFalse(ChessCore.canMove(7, 7, 7, 3))
    }
}
