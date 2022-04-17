package com.sc19kb.android.chessapplication

import org.junit.Assert.*
import org.junit.Test

class ChessCoreTest {

    @Test
    fun clear() {
        print("TESTING: clear(): ")
        assertNotNull(ChessCore.pieceAt(0, 0))
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        assertNull(ChessCore.pieceAt(0, 0))
        println("PASS")
    }


    @Test
    fun movePiece_isCorrect() {
        print("TESTING: movePiece_isCorrect() : ")
        assertNotNull(ChessCore.pieceAt(0, 1))
        assertNull(ChessCore.pieceAt(0, 2))
        ChessCore.movePiece(0, 1, 0, 2)
        assertNotNull(ChessCore.pieceAt(0, 2))
        println("PASS")
    }

    @Test
    fun reset_isCorrect() {
        print("TESTING: reset_isCorrect() : ")

        // move leftmost white pawn one square forward
        assertNotNull(ChessCore.pieceAt(0,1))
        assertNull(ChessCore.pieceAt(0, 2))
        ChessCore.movePiece(0, 1, 0, 2)
        assertNotNull(ChessCore.pieceAt(0, 2))

        // move the black pawn in front of the black king two squares forward
        assertNotNull(ChessCore.pieceAt(4,6))
        assertNull(ChessCore.pieceAt(4, 4))
        ChessCore.movePiece(4, 6, 4, 4)
        assertNull(ChessCore.pieceAt(4,6))
        assertNotNull(ChessCore.pieceAt(4, 4))

        // put everything back in place
        ChessCore.reset()
        assertNull(ChessCore.pieceAt(0, 2))
        assertNotNull(ChessCore.pieceAt(0, 1))
        assertNull(ChessCore.pieceAt(4, 4))
        assertNotNull(ChessCore.pieceAt(4, 6))

        println("PASS")

    }

    @Test
    fun pieceAt_isCorrect() {
        print("TESTING: pieceAt_isCorrect() : ")
        // point to second from the left black pawn
        assertNotNull(ChessCore.pieceAt(6, 1))
        assertEquals(ChessArmy.BLACK, ChessCore.pieceAt(1, 6)?.army)
        assertEquals(ChessRank.PAWN, ChessCore.pieceAt(1, 6)?.rank)

        // point to right white rook
        assertNotNull(ChessCore.pieceAt(7, 0))
        assertEquals(ChessArmy.WHITE, ChessCore.pieceAt(7, 0)?.army)
        assertEquals(ChessRank.ROOK, ChessCore.pieceAt(7, 0)?.rank)

        // point to white queen
        assertNotNull(ChessCore.pieceAt(3, 0))
        assertEquals(ChessArmy.WHITE, ChessCore.pieceAt(3, 0)?.army)
        assertEquals(ChessRank.QUEEN, ChessCore.pieceAt(3, 0)?.rank)

        // point to empty square
        assertNull(ChessCore.pieceAt(4, 4))

        println("PASS")
    }


    @Test
    fun isKingCheckedMated_isCorrect() {
        print("TESTING: isKingCheckedMated_isCorrect() : ")

        // 1. If King is in the Top Left Corner
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(0, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(1, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(7, 6, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessCore.isKingCheckMated(ChessArmy.WHITE))
        println("PASS: 1. Top Left Corner")

        // 2. If King is in the Top Right Corner
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(7, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(6, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(1, 6, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessCore.isKingCheckMated(ChessArmy.WHITE))
        println("PASS: 2. Top Right Corner")

        // 3. If King is in the Bottom Left Corner
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(0, 0, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(3, 1, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(1, 2, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessCore.isKingCheckMated(ChessArmy.WHITE))
        println("PASS: 3. Bottom Left Corner")

        // 4. If King is in the Bottom Right Corner
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(7, 0, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(0, 1, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(6, 7, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessCore.isKingCheckMated(ChessArmy.WHITE))
        println("PASS: 4. Bottom Right Corner")

        // 5. If King is in the Top Row (not corner)
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(3, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(2, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(3, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(4, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessCore.isKingCheckMated(ChessArmy.WHITE))
        println("PASS: 5. Top Row (not corner)")

        // 6. If King is in the Bottom Row (not corner)
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(3, 0, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(2, 7, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(3, 7, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(4, 7, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessCore.isKingCheckMated(ChessArmy.WHITE))
        println("PASS: 6. Bottom Row (not corner)")

        // 7. If King is in the Lefmost Column (not corner)
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(0, 3, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(7, 2, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(7, 3, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(7, 4, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        ChessCore.movePiece(2,0,1,0)
        assertTrue(ChessCore.isKingCheckMated(ChessArmy.WHITE))
        println("PASS: 7. Leftmost Column (not corner)")

        // 8. If King is in the Rightmost Column (not corner)
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(7, 3, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(0, 2, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(0, 3, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(0, 4, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessCore.isKingCheckMated(ChessArmy.WHITE))
        println("PASS: 8. Rightmost Column (not corner)")

        // 9. If King is in the Rest of the Board
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(3, 5, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(1, 6, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(7, 5, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        ChessCore.piecesSet.add(ChessPiece(7, 4, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))

        assertTrue(ChessCore.isKingCheckMated(ChessArmy.WHITE))
        println("PASS: 9. Rest of the Board")

        println("PASS")
    }


}