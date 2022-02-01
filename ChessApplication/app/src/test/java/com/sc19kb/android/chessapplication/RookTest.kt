package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp

class RookTest {

    @Test
    fun canMoveRook_isCorrect() {
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))
        assertTrue(ChessBoardConsole.canMove(4, 4, 4, 0))
        assertTrue(ChessBoardConsole.canMove(4, 4, 4, 6))
        assertTrue(ChessBoardConsole.canMove(4, 4, 1, 4))
        assertTrue(ChessBoardConsole.canMove(4, 4, 7, 4))

        assertFalse(ChessBoardConsole.canMove(4, 4, 3, 2))
        assertFalse(ChessBoardConsole.canMove(4, 4, 5, 5))
        assertFalse(ChessBoardConsole.canMove(4, 4, 1, 3))
        assertFalse(ChessBoardConsole.canMove(4, 4, 7, 0))

        ChessBoardConsole.reset()
        assertFalse(ChessBoardConsole.canMove(0, 7, 0, 3))
        assertFalse(ChessBoardConsole.canMove(0, 0, 5, 0))
        assertFalse(ChessBoardConsole.canMove(7, 0, 0, 5))
        assertFalse(ChessBoardConsole.canMove(7, 7, 7, 3))
    }
}
