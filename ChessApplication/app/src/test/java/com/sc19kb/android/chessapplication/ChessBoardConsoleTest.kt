package com.sc19kb.android.chessapplication

import com.sc19kb.android.chessapplication.ChessBoardConsole
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp
import kotlin.math.round

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


    @Test
    fun isKingCheckedMated_isCorrect() {
        print("TESTING: isKingCheckedMated_isCorrect() : ")

        // 1. If King is in the Top Left Corner
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(0, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(1, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 6, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessBoardConsole.isKingCheckedMated(ChessArmy.WHITE))
        println("PASS: 1. Top Left Corner")

        // 2. If King is in the Top Right Corner
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(6, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(1, 6, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessBoardConsole.isKingCheckedMated(ChessArmy.WHITE))
        println("PASS: 2. Top Right Corner")

        // 3. If King is in the Bottom Left Corner
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(0, 0, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(3, 1, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(1, 2, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessBoardConsole.isKingCheckedMated(ChessArmy.WHITE))
        println("PASS: 3. Bottom Left Corner")

        // 4. If King is in the Bottom Right Corner
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 0, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(0, 1, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(6, 7, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessBoardConsole.isKingCheckedMated(ChessArmy.WHITE))
        println("PASS: 4. Bottom Right Corner")

        // 5. If King is in the Top Row (not corner)
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(3, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(2, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(3, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(4, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessBoardConsole.isKingCheckedMated(ChessArmy.WHITE))
        println("PASS: 5. Top Row (not corner)")

        // 6. If King is in the Bottom Row (not corner)
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(3, 0, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(2, 7, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(3, 7, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(4, 7, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessBoardConsole.isKingCheckedMated(ChessArmy.WHITE))
        println("PASS: 6. Bottom Row (not corner)")

        // 7. If King is in the Lefmost Column (not corner)
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(0, 3, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 2, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 3, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 4, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        ChessBoardConsole.movePiece(2,0,1,0)
        assertTrue(ChessBoardConsole.isKingCheckedMated(ChessArmy.WHITE))
        println("PASS: 7. Leftmost Column (not corner)")

        // 8. If King is in the Rightmost Column (not corner)
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 3, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(0, 2, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(0, 3, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(0, 4, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessBoardConsole.isKingCheckedMated(ChessArmy.WHITE))
        println("PASS: 8. Rightmost Column (not corner)")

        // 9. If King is in the Rest of the Board
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(3, 5, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(1, 6, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 5, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 4, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessBoardConsole.isKingCheckedMated(ChessArmy.WHITE))
        println("PASS: 9. Rest of the Board")

        println("PASS")
    }


}