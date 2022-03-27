package com.sc19kb.android.chessapplication

import com.sc19kb.android.chessapplication.ChessBoardConsole.blackLeftCastleFlag
import com.sc19kb.android.chessapplication.ChessBoardConsole.blackRightCastleFlag
import com.sc19kb.android.chessapplication.ChessBoardConsole.whiteLeftCastleFlag
import com.sc19kb.android.chessapplication.ChessBoardConsole.whiteRightCastleFlag
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp

class KingTest {

    @Test
    fun canMoveKing_isCorrect() {
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)
        ChessBoardConsole.piecesSet.add(ChessPiece(4, 4, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
        assertTrue(ChessBoardConsole.canMove(4, 4, 3, 5))
        assertTrue(ChessBoardConsole.canMove(4, 4, 4, 5))
        assertTrue(ChessBoardConsole.canMove(4, 4, 5, 5))
        assertTrue(ChessBoardConsole.canMove(4, 4, 3, 4))
        assertTrue(ChessBoardConsole.canMove(4, 4, 5, 4))
        assertTrue(ChessBoardConsole.canMove(4, 4, 3, 3))
        assertTrue(ChessBoardConsole.canMove(4, 4, 4, 3))
        assertTrue(ChessBoardConsole.canMove(4, 4, 5, 3))

        assertFalse(ChessBoardConsole.canMove(4, 4, 1, 7))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 1, 1))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 2))
        assertFalse(ChessBoardConsole.canMove(4, 4, 4, 0))
        assertFalse(ChessBoardConsole.canMove(4, 4, 4, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 1, 4))
        assertFalse(ChessBoardConsole.canMove(4, 4, 7, 4))
        assertFalse(ChessBoardConsole.canMove(4, 4, 2, 5))
        assertFalse(ChessBoardConsole.canMove(4, 4, 3, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 5, 6))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 5))
        assertFalse(ChessBoardConsole.canMove(4, 4, 2, 3))
        assertFalse(ChessBoardConsole.canMove(4, 4, 3, 2))
        assertFalse(ChessBoardConsole.canMove(4, 4, 6, 3))
        assertFalse(ChessBoardConsole.canMove(4, 4, 5, 2))
    }

    @Test
    fun leftCastle_isCorrect() {
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)

        ChessBoardConsole.piecesSet.add(ChessPiece(4, 0, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(0, 0, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))
        println(ChessBoardConsole)
        whiteLeftCastleFlag = true
        ChessBoardConsole.Castle(ChessArmy.WHITE, 4, 0, 0, 0, true)

        assertNotNull(ChessBoardConsole.pieceAt(2, 0))
        assertEquals(ChessArmy.WHITE, ChessBoardConsole.pieceAt(2, 0)?.army)
        assertEquals(ChessRank.KING, ChessBoardConsole.pieceAt(2, 0)?.rank)

        assertNotNull(ChessBoardConsole.pieceAt(3, 0))
        assertEquals(ChessArmy.WHITE, ChessBoardConsole.pieceAt(3, 0)?.army)
        assertEquals(ChessRank.ROOK, ChessBoardConsole.pieceAt(3, 0)?.rank)
        println(ChessBoardConsole)

        ChessBoardConsole.piecesSet.add(ChessPiece(4, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(0, 7, ChessArmy.BLACK, ChessRank.ROOK, R.drawable.rook_black))
        println(ChessBoardConsole)
        blackLeftCastleFlag = true
        ChessBoardConsole.Castle(ChessArmy.BLACK, 4, 7, 0, 7, true)

        assertNotNull(ChessBoardConsole.pieceAt(2, 7))
        assertEquals(ChessArmy.BLACK, ChessBoardConsole.pieceAt(2, 7)?.army)
        assertEquals(ChessRank.KING, ChessBoardConsole.pieceAt(2, 7)?.rank)

        assertNotNull(ChessBoardConsole.pieceAt(3, 7))
        assertEquals(ChessArmy.BLACK, ChessBoardConsole.pieceAt(3, 7)?.army)
        assertEquals(ChessRank.ROOK, ChessBoardConsole.pieceAt(3, 7)?.rank)
        println(ChessBoardConsole)

    }

    @Test
    fun rightCastle_isCorrect() {
        ChessBoardConsole.piecesSet.removeAll(ChessBoardConsole.piecesSet)

        ChessBoardConsole.piecesSet.add(ChessPiece(4, 0, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 0, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))
        println(ChessBoardConsole)
        whiteRightCastleFlag = true
        ChessBoardConsole.Castle(ChessArmy.WHITE, 4, 0, 7, 0, false)

        assertNotNull(ChessBoardConsole.pieceAt(6, 0))
        assertEquals(ChessArmy.WHITE, ChessBoardConsole.pieceAt(6, 0)?.army)
        assertEquals(ChessRank.KING, ChessBoardConsole.pieceAt(6, 0)?.rank)

        assertNotNull(ChessBoardConsole.pieceAt(5, 0))
        assertEquals(ChessArmy.WHITE, ChessBoardConsole.pieceAt(5, 0)?.army)
        assertEquals(ChessRank.ROOK, ChessBoardConsole.pieceAt(5, 0)?.rank)
        println(ChessBoardConsole)

        ChessBoardConsole.piecesSet.add(ChessPiece(4, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
        ChessBoardConsole.piecesSet.add(ChessPiece(7, 7, ChessArmy.BLACK, ChessRank.ROOK, R.drawable.rook_black))
        println(ChessBoardConsole)
        blackRightCastleFlag = true
        ChessBoardConsole.Castle(ChessArmy.BLACK, 4, 7, 7, 7, false)

        assertNotNull(ChessBoardConsole.pieceAt(6, 7))
        assertEquals(ChessArmy.BLACK, ChessBoardConsole.pieceAt(6, 7)?.army)
        assertEquals(ChessRank.KING, ChessBoardConsole.pieceAt(6, 7)?.rank)

        assertNotNull(ChessBoardConsole.pieceAt(5, 7))
        assertEquals(ChessArmy.BLACK, ChessBoardConsole.pieceAt(5, 7)?.army)
        assertEquals(ChessRank.ROOK, ChessBoardConsole.pieceAt(5, 7)?.rank)
        println(ChessBoardConsole)
    }
}
