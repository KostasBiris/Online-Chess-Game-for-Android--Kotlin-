package com.sc19kb.android.chessapplication

/*
* ---------- CHESS BOARD (CONSOLE) ------------
* 
* Class that prints the chessboard with
* all its pieces on the console. Used for
* easier development of the app, without
* the mobile phone user interface.
*
*/

import android.util.Log


class ChessBoardConsole {

    var piecesSet = mutableSetOf<ChessPiece>()

    //Clear the chessboardString and re-arrange the pieces
    init {
        reset()
    }


    fun movePiece(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int) {
        
        // If we try to move the piece to square where it is already located
        if (curColumn == destColumn && curRow == destRow) return

        // Select the piece you want to move is located by getting it from its square
        val selectedPiece = pieceAt(curColumn, curRow) ?: return

        pieceAt(destColumn, destRow)?.let {
            if (it.army == selectedPiece.army) {
                return
            }
            piecesSet.remove(it)
        }

        // If there is an enemy piece in that square, remove it and put
        // the selected piece in that square
        piecesSet.remove(selectedPiece)
        piecesSet.add(ChessPiece(destColumn, destRow, selectedPiece.army, selectedPiece.rank, selectedPiece.resID))

    }
    
    
    private fun reset() {
        // Clear the Chessboard before re-arranging the pieces
        piecesSet.removeAll(piecesSet)

        //Kings and Queens are unique pieces, so we arrange them by one
        piecesSet.add(ChessPiece(3, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        piecesSet.add(ChessPiece(3, 7, ChessArmy.BLACK, ChessRank.QUEEN, R.drawable.queen_black))
        piecesSet.add(ChessPiece(4, 0, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
        piecesSet.add(ChessPiece(4, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))

        // Bishops, Knights and Rooks have come in pairs of 2 so we arrange them by two
        for (i in 0..1) {
            piecesSet.add(ChessPiece(2 + i * 3, 0, ChessArmy.WHITE, ChessRank.BISHOP, R.drawable.bishop_white))
            piecesSet.add(ChessPiece(2 + i * 3, 7, ChessArmy.BLACK, ChessRank.BISHOP, R.drawable.bishop_black))

            piecesSet.add(ChessPiece(1 + i * 5, 0, ChessArmy.WHITE, ChessRank.KNIGHT, R.drawable.knight_white))
            piecesSet.add(ChessPiece(1 + i * 5, 7, ChessArmy.BLACK, ChessRank.KNIGHT, R.drawable.knight_black))

            piecesSet.add(ChessPiece(0 + i * 7, 0, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))
            piecesSet.add(ChessPiece(0 + i * 7, 7, ChessArmy.BLACK, ChessRank.ROOK, R.drawable.rook_black))

        }

        // Pawns come in two groups of 8
        for (i in 0..7) {
            piecesSet.add(ChessPiece(i, 1, ChessArmy.WHITE, ChessRank.PAWN, R.drawable.pawn_white))
            piecesSet.add(ChessPiece(i, 6, ChessArmy.BLACK, ChessRank.PAWN, R.drawable.pawn_black))
        }
    }

    // Returns the rank of the piece at a certain square, if there is a piece on that square
    fun pieceAt(col: Int, row: Int) : ChessPiece? {
        for (piece in piecesSet) {
            if (col == piece.col && row == piece.row) {
                return piece
            }
        }
        return null
    }



    // Turns the chess board into a printable string
    override fun toString(): String {

        //the String that contains the chessboard
        var boardString = " \n"
        
        // add every row
        for (row in 7 downTo 0) {
            boardString += "$row"
            
            // add every column in a row
            for (col in 0..7) {

                // check if there is a piece on that square
                val piece = pieceAt(col, row)

                // if there is no piece put a dot to indicate the empty square
                if (piece == null) {
                    boardString += " ."

                // if there is a piece mark it according to its rank and colour
                // WHITES are CAPITAL case  ,  BLACKS are Lower case
                } else {
                    val white = piece.army == ChessArmy.WHITE
                    boardString += " "
                    boardString += when (piece.rank) {
                        ChessRank.KING -> {
                            if (white) "K" else "k"
                        }
                        ChessRank.QUEEN -> {
                            if (white) "Q" else "q"
                        }
                        ChessRank.BISHOP -> {
                            if (white) "B" else "b"
                        }
                        ChessRank.ROOK -> {
                            if (white) "R" else "r"
                        }
                        ChessRank.KNIGHT -> {
                            if (white) "H" else "h"
                        }
                        ChessRank.PAWN -> {
                            if (white) "P" else "p"
                        }
                    }
                }
            }
            boardString += "\n"
        }

        boardString += "  0 1 2 3 4 5 6 7"

        return boardString
    }
}