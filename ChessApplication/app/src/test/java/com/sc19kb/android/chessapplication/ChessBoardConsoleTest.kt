package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp

class ChessBoardConsoleTest {

    @Test
    fun clear() {
        print("TESTING: clear(): ")
        assertNotNull(ChessBoardConsole.pieceAt(0, 0))
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        assertNull(ChessBoardConsole.pieceAt(0, 0))
        println("PASS")
    }


    @Test
    fun toString_isCorrect() {
        print("TESTING: toString_isCorrect() : ")
        val expected: String = "\n7 r h b q k b h r\n" + "6 p p p p p p p p\n" + "5 . . . . . . . .\n" + "4 . . . . . . . .\n" + "3 . . . . . . . .\n" + "2 . . . . . . . .\n" + "1 P P P P P P P P\n" + "0 R H B Q K B H R\n" + "  0 1 2 3 4 5 6 7"
        val actual: String = ChessBoardConsole.toString()
       // println(ChessBoardConsole)
        assertEquals(expected,actual)
        println("PASS")
    }

    @Test
    fun movePiece_isCorrect() {
        print("TESTING: movePiece_isCorrect() : ")
        assertNotNull(ChessBoardConsole.pieceAt(0, 1))
        assertNull(ChessBoardConsole.pieceAt(0, 2))
        ChessBoardConsole.movePiece(0, 1, 0, 2)
        assertNotNull(ChessBoardConsole.pieceAt(0, 2))
        println("PASS")
    }

    @Test
    fun reset_isCorrect() {
        print("TESTING: reset_isCorrect() : ")

        // move leftmost white pawn one square forward
        assertNotNull(ChessBoardConsole.pieceAt(0,1))
        assertNull(ChessBoardConsole.pieceAt(0, 2))
        ChessBoardConsole.movePiece(0, 1, 0, 2)
        assertNotNull(ChessBoardConsole.pieceAt(0, 2))

        // move the black pawn in front of the black king two squares forward
        assertNotNull(ChessBoardConsole.pieceAt(4,6))
        assertNull(ChessBoardConsole.pieceAt(4, 4))
        ChessBoardConsole.movePiece(4, 6, 4, 4)
        assertNull(ChessBoardConsole.pieceAt(4,6))
        assertNotNull(ChessBoardConsole.pieceAt(4, 4))

        // put everything back in place
        ChessBoardConsole.reset()
        assertNull(ChessBoardConsole.pieceAt(0, 2))
        assertNotNull(ChessBoardConsole.pieceAt(0, 1))
        assertNull(ChessBoardConsole.pieceAt(4, 4))
        assertNotNull(ChessBoardConsole.pieceAt(4, 6))

        println("PASS")

    }

    @Test
    fun pieceAt_isCorrect() {
        print("TESTING: pieceAt_isCorrect() : ")
        // point to second from the left black pawn
        assertNotNull(ChessBoardConsole.pieceAt(6, 1))
        assertEquals(ChessArmy.BLACK, ChessBoardConsole.pieceAt(1, 6)?.army)
        assertEquals(ChessRank.PAWN, ChessBoardConsole.pieceAt(1, 6)?.rank)

        // point to right white rook
        assertNotNull(ChessBoardConsole.pieceAt(7, 0))
        assertEquals(ChessArmy.WHITE, ChessBoardConsole.pieceAt(7, 0)?.army)
        assertEquals(ChessRank.ROOK, ChessBoardConsole.pieceAt(7, 0)?.rank)

        // point to white queen
        assertNotNull(ChessBoardConsole.pieceAt(3, 0))
        assertEquals(ChessArmy.WHITE, ChessBoardConsole.pieceAt(3, 0)?.army)
        assertEquals(ChessRank.QUEEN, ChessBoardConsole.pieceAt(3, 0)?.rank)

        // point to empty square
        assertNull(ChessBoardConsole.pieceAt(4, 4))

        println("PASS")
    }

}