package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test

class PawnTest {

    @Test
    fun firstMovePawn_isCorrect() {
        println(ChessCore)
        // WHITE
        // one step
        assertTrue(ChessCore.canPawnMove(ChessArmy.WHITE, 0, 1, 0, 2))
        println("PASS 1")
        // two steps
        assertTrue(ChessCore.canPawnMove(ChessArmy.WHITE, 0, 1, 0, 3))
        println("PASS 2")
        // three steps
        assertFalse(ChessCore.canPawnMove(ChessArmy.WHITE, 0, 1, 0, 4))
        println("PASS 3")

        // BLACK
        // one step
        assertTrue(ChessCore.canPawnMove(ChessArmy.BLACK, 0, 6, 0, 5))
        println("PASS 4")
        // two steps
        assertTrue(ChessCore.canPawnMove(ChessArmy.BLACK, 0, 6, 0, 4))
        println("PASS 5")
        // three steps
        assertFalse(ChessCore.canPawnMove(ChessArmy.BLACK, 0, 6, 0, 3))
        println("PASS 6")
    }

    @Test
    fun pawnCapture_isCorrect() {
        println(ChessCore)
        // capture with white pawn
        ChessCore.piecesSet.add(ChessPiece(0, 5, ChessArmy.WHITE, ChessRank.PAWN, R.drawable.pawn_white))
        assertTrue(ChessCore.canPawnMove(ChessArmy.WHITE, 0, 5, 1, 6))
        ChessCore.movePiece(0, 5, 1, 6)
        println(ChessCore)

        // capture with black pawn
        ChessCore.piecesSet.add(ChessPiece(5, 2, ChessArmy.BLACK, ChessRank.PAWN, R.drawable.pawn_black))
        assertTrue(ChessCore.canPawnMove(ChessArmy.BLACK, 5, 2, 4, 1))
        ChessCore.movePiece(5, 2, 4, 1)
        println(ChessCore)
    }

    @Test
    fun pawnEnPassant_isCorrect() {
        ChessCore.piecesSet.add(ChessPiece(4, 3, ChessArmy.WHITE, ChessRank.PAWN, R.drawable.pawn_white))
        ChessCore.movePiece(4,3,4,4)

        assertTrue(ChessCore.canPawnMove(ChessArmy.BLACK,5, 6, 5, 4))

        ChessCore.movePiece(5,6,5,4)
        ChessCore.whiteEnPassantFlag = 5
        assertNotNull(ChessCore.pieceAt(5, 4))
        assertEquals(ChessArmy.BLACK, ChessCore.pieceAt(5, 4)?.army)
        assertEquals(ChessRank.PAWN, ChessCore.pieceAt(5, 4)?.rank)

        assertTrue(ChessCore.canPawnMove(ChessArmy.WHITE,4, 4, 5, 5))
        ChessCore.enPassant(ChessArmy.WHITE,4,4,5,5)
        assertNull(ChessCore.pieceAt(5, 4))

    }
}
