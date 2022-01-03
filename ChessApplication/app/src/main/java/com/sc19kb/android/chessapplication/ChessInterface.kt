package com.sc19kb.android.chessapplication

interface ChessInterface {
    fun pieceAt(column: Int, row: Int) : ChessPiece?
    fun movePiece(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int)
}