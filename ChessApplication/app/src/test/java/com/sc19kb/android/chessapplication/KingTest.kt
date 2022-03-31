package com.sc19kb.android.chessapplication

import com.sc19kb.android.chessapplication.ChessCore.blackLeftCastleFlag
import com.sc19kb.android.chessapplication.ChessCore.blackRightCastleFlag
import com.sc19kb.android.chessapplication.ChessCore.whiteLeftCastleFlag
import com.sc19kb.android.chessapplication.ChessCore.whiteRightCastleFlag
import org.junit.Assert.*
import org.junit.Test

class KingTest {

    @Test
    fun canMoveKing_isCorrect() {
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)
        ChessCore.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
        assertTrue(ChessCore.canMove(4, 4, 3, 5))
        assertTrue(ChessCore.canMove(4, 4, 4, 5))
        assertTrue(ChessCore.canMove(4, 4, 5, 5))
        assertTrue(ChessCore.canMove(4, 4, 3, 4))
        assertTrue(ChessCore.canMove(4, 4, 5, 4))
        assertTrue(ChessCore.canMove(4, 4, 3, 3))
        assertTrue(ChessCore.canMove(4, 4, 4, 3))
        assertTrue(ChessCore.canMove(4, 4, 5, 3))

        assertFalse(ChessCore.canMove(4, 4, 1, 7))
        assertFalse(ChessCore.canMove(4, 4, 6, 6))
        assertFalse(ChessCore.canMove(4, 4, 1, 1))
        assertFalse(ChessCore.canMove(4, 4, 6, 2))
        assertFalse(ChessCore.canMove(4, 4, 4, 0))
        assertFalse(ChessCore.canMove(4, 4, 4, 6))
        assertFalse(ChessCore.canMove(4, 4, 1, 4))
        assertFalse(ChessCore.canMove(4, 4, 7, 4))
        assertFalse(ChessCore.canMove(4, 4, 2, 5))
        assertFalse(ChessCore.canMove(4, 4, 3, 6))
        assertFalse(ChessCore.canMove(4, 4, 5, 6))
        assertFalse(ChessCore.canMove(4, 4, 6, 5))
        assertFalse(ChessCore.canMove(4, 4, 2, 3))
        assertFalse(ChessCore.canMove(4, 4, 3, 2))
        assertFalse(ChessCore.canMove(4, 4, 6, 3))
        assertFalse(ChessCore.canMove(4, 4, 5, 2))
    }

    @Test
    fun leftCastle_isCorrect() {
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)

        ChessCore.piecesSet.add(ChessPiece(4, 0, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
        ChessCore.piecesSet.add(ChessPiece(0, 0, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))
        println(ChessCore)
        whiteLeftCastleFlag = true
        ChessCore.castle(ChessArmy.WHITE, 4, 0, 0, 0, true)

        assertNotNull(ChessCore.pieceAt(2, 0))
        assertEquals(ChessArmy.WHITE, ChessCore.pieceAt(2, 0)?.army)
        assertEquals(ChessRank.KING, ChessCore.pieceAt(2, 0)?.rank)

        assertNotNull(ChessCore.pieceAt(3, 0))
        assertEquals(ChessArmy.WHITE, ChessCore.pieceAt(3, 0)?.army)
        assertEquals(ChessRank.ROOK, ChessCore.pieceAt(3, 0)?.rank)
        println(ChessCore)

        ChessCore.piecesSet.add(ChessPiece(4, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(0, 7, ChessArmy.BLACK, ChessRank.ROOK, R.drawable.rook_black))
        println(ChessCore)
        blackLeftCastleFlag = true
        ChessCore.castle(ChessArmy.BLACK, 4, 7, 0, 7, true)

        assertNotNull(ChessCore.pieceAt(2, 7))
        assertEquals(ChessArmy.BLACK, ChessCore.pieceAt(2, 7)?.army)
        assertEquals(ChessRank.KING, ChessCore.pieceAt(2, 7)?.rank)

        assertNotNull(ChessCore.pieceAt(3, 7))
        assertEquals(ChessArmy.BLACK, ChessCore.pieceAt(3, 7)?.army)
        assertEquals(ChessRank.ROOK, ChessCore.pieceAt(3, 7)?.rank)
        println(ChessCore)

    }

    @Test
    fun rightCastle_isCorrect() {
        ChessCore.piecesSet.removeAll(ChessCore.piecesSet)

        ChessCore.piecesSet.add(ChessPiece(4, 0, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
        ChessCore.piecesSet.add(ChessPiece(7, 0, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))
        println(ChessCore)
        whiteRightCastleFlag = true
        ChessCore.castle(ChessArmy.WHITE, 4, 0, 7, 0, false)

        assertNotNull(ChessCore.pieceAt(6, 0))
        assertEquals(ChessArmy.WHITE, ChessCore.pieceAt(6, 0)?.army)
        assertEquals(ChessRank.KING, ChessCore.pieceAt(6, 0)?.rank)

        assertNotNull(ChessCore.pieceAt(5, 0))
        assertEquals(ChessArmy.WHITE, ChessCore.pieceAt(5, 0)?.army)
        assertEquals(ChessRank.ROOK, ChessCore.pieceAt(5, 0)?.rank)
        println(ChessCore)

        ChessCore.piecesSet.add(ChessPiece(4, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessCore.piecesSet.add(ChessPiece(7, 7, ChessArmy.BLACK, ChessRank.ROOK, R.drawable.rook_black))
        println(ChessCore)
        blackRightCastleFlag = true
        ChessCore.castle(ChessArmy.BLACK, 4, 7, 7, 7, false)

        assertNotNull(ChessCore.pieceAt(6, 7))
        assertEquals(ChessArmy.BLACK, ChessCore.pieceAt(6, 7)?.army)
        assertEquals(ChessRank.KING, ChessCore.pieceAt(6, 7)?.rank)

        assertNotNull(ChessCore.pieceAt(5, 7))
        assertEquals(ChessArmy.BLACK, ChessCore.pieceAt(5, 7)?.army)
        assertEquals(ChessRank.ROOK, ChessCore.pieceAt(5, 7)?.rank)
        println(ChessCore)
    }
}
