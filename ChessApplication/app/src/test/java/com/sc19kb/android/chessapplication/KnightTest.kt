package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test

class KnightTest {

    @Test
    fun canMoveKnight_isCorrect() {
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.KNIGHT, R.drawable.knight_white))
        println(ChessCore)
        assertTrue(ChessCore.canMove(4, 4, 2, 5))
        assertTrue(ChessCore.canMove(4, 4, 3, 6))
        assertTrue(ChessCore.canMove(4, 4, 5, 6))
        assertTrue(ChessCore.canMove(4, 4, 6, 5))
        assertTrue(ChessCore.canMove(4, 4, 2, 3))
        assertTrue(ChessCore.canMove(4, 4, 3, 2))
        assertTrue(ChessCore.canMove(4, 4, 6, 3))
        assertTrue(ChessCore.canMove(4, 4, 5, 2))

        assertFalse(ChessCore.canMove(4, 4, 3, 5))
        assertFalse(ChessCore.canMove(4, 4, 5, 3))
        assertFalse(ChessCore.canMove(4, 4, 1, 4))
        assertFalse(ChessCore.canMove(4, 4, 4, 1))
        assertFalse(ChessCore.canMove(4, 4, 6, 4))
        assertFalse(ChessCore.canMove(4, 4, 4, 6))
    }
}
