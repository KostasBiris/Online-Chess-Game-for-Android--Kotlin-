package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp

class KnightTest {

    @Test
    fun canMoveKnight_isCorrect() {
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.KNIGHT, R.drawable.knight_white))
        println(ChessBoardConsole)
        assertTrue(ChessBoardConsole.canMove(4, 4, 2, 5))
        assertTrue(ChessBoardConsole.canMove(4, 4, 3, 6))
        assertTrue(ChessBoardConsole.canMove(4, 4, 5, 6))
        assertTrue(ChessBoardConsole.canMove(4, 4, 6, 5))
        assertTrue(ChessBoardConsole.canMove(4, 4, 2, 3))
        assertTrue(ChessBoardConsole.canMove(4, 4, 3, 2))
        assertTrue(ChessBoardConsole.canMove(4, 4, 6, 3))
        assertTrue(ChessBoardConsole.canMove(4, 4, 5, 2))

        assertFalse(ChessBoardConsole.canMove(4, 4, 3, 5))
        assertFalse(ChessBoardConsole.canMove(4, 4, 5, 3))
        assertFalse(ChessBoardConsole.canMove(4, 4, 1, 4))
        assertFalse(ChessBoardConsole.canMove(4, 4, 4, 1))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 4))
        assertFalse(ChessBoardConsole.canMove(4, 4, 4, 6))
    }
}
