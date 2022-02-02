package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp

class PawnTest {

    @Test
    fun firstMovePawn_isCorrect() {
        println(ChessBoardConsole)
        // WHITE
        // one step
        assertTrue(ChessBoardConsole.canPawnMove(ChessArmy.WHITE, 0, 1, 0, 2))
        println("PASS 1")
        // two steps
        assertTrue(ChessBoardConsole.canPawnMove(ChessArmy.WHITE, 0, 1, 0, 3))
        println("PASS 2")
        // three steps
        assertFalse(ChessBoardConsole.canPawnMove(ChessArmy.WHITE, 0, 1, 0, 4))
        println("PASS 3")

        // BLACK
        // one step
        assertTrue(ChessBoardConsole.canPawnMove(ChessArmy.BLACK, 0, 6, 0, 5))
        println("PASS 4")
        // two steps
        assertTrue(ChessBoardConsole.canPawnMove(ChessArmy.BLACK, 0, 6, 0, 4))
        println("PASS 5")
        // three steps
        assertFalse(ChessBoardConsole.canPawnMove(ChessArmy.BLACK, 0, 6, 0, 3))
        println("PASS 6")
    }

    @Test
    fun pawnCapture_isCorrect() {
        println(ChessBoardConsole)
        // capture with white pawn
        ChessBoardConsole.piecesSet.add(ChessPiece(0, 5, ChessArmy.WHITE, ChessRank.PAWN, R.drawable.pawn_white))
        assertTrue(ChessBoardConsole.canPawnMove(ChessArmy.WHITE, 0, 5, 1, 6))
        ChessBoardConsole.movePiece(0, 5, 1, 6)
        println(ChessBoardConsole)

        // capture with black pawn
        ChessBoardConsole.piecesSet.add(ChessPiece(5, 2, ChessArmy.BLACK, ChessRank.PAWN, R.drawable.pawn_black))
        assertTrue(ChessBoardConsole.canPawnMove(ChessArmy.BLACK, 5, 2, 4, 1))
        ChessBoardConsole.movePiece(5, 2, 4, 1)
        println(ChessBoardConsole)
    }

    @Test
    fun pawnEnPassant_isCorrect() {
        ChessBoardConsole.piecesSet.add(ChessPiece(4, 3, ChessArmy.WHITE, ChessRank.PAWN, R.drawable.pawn_white))
        ChessBoardConsole.movePiece(4,3,4,4)

        assertTrue(ChessBoardConsole.canPawnMove(ChessArmy.BLACK,5, 6, 5, 4))

        ChessBoardConsole.movePiece(5,6,5,4)
        whiteEnPassantFlag = 5
        assertNotNull(ChessBoardConsole.pieceAt(5, 4))
        assertEquals(ChessArmy.BLACK, ChessBoardConsole.pieceAt(5, 4)?.army)
        assertEquals(ChessRank.PAWN, ChessBoardConsole.pieceAt(5, 4)?.rank)

        assertTrue(ChessBoardConsole.canPawnMove(ChessArmy.WHITE,4, 4, 5, 5))
        ChessBoardConsole.enPassant(ChessArmy.WHITE,4,4,5,5)
        assertNull(ChessBoardConsole.pieceAt(5, 4))

    }
}
