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

import java.lang.Math.abs


class ChessBoardConsole {

    var piecesSet = mutableSetOf<ChessPiece>()

    //Clear the chessboardString and re-arrange the pieces
    init {
        reset()
    }

//===============================================================================================

    // Check if there is empty vertical space between the current square and the destination square.
    private fun isGapVertically(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (curColumn != destColumn) {
            return false
        }else{
            2
            val emptySquares = abs(curRow - destRow) - 1

            if (emptySquares == 0 ) return true

            for (i in 1..emptySquares) {
                val nextRow = when {
                    destRow > curRow -> curRow + i
                    else -> curRow - i
                }

                if (pieceAt(curColumn, nextRow) != null) {
                    return false
                }
            }
            return true
        }

    }

    // Check if there is empty horizontal space between the current square and the destination square.
    private fun isGapHorizontally(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (curRow != destRow) return false

        val emptySquares = abs(curColumn - destColumn) - 1

        if (emptySquares == 0 ) return true

        for (i in 1..emptySquares) {
            val nextColumn = when {
                destColumn > curColumn -> curColumn + i
                else -> curColumn - i
            }

            if (pieceAt(nextColumn, curRow) != null) {
                return false
            }
        }
        return true
    }

    // Check if there is empty diagonal space between the current square and the destination square.
    private fun isGapDiagonally(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {

        if (abs(curColumn - destColumn) != abs(curRow - destRow)) return false

        val emptySquares = abs(curColumn - destColumn) - 1

        for (i in 1..emptySquares) {
            val nextColumn = when {
                destColumn > curColumn -> curColumn + i
                else -> curColumn - i
            }

            val nextRow = when {
                destRow > curRow -> curRow + i
                else -> curRow - i
            }

            if (pieceAt(nextColumn, nextRow) != null) {
                return false
            }
        }
        return true
    }

    // Pawns can only move one Square forward but are able to move two Squares on their first move.
    private fun canPawnMove(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (curColumn == destColumn) {
            if (curRow == 1) {
                return destRow == 2 || destRow == 3

            }else if (!isGapDiagonally(curColumn, curRow, destColumn, destRow)){
                return destRow == curRow+1 && (destColumn == curColumn-1 || destColumn == curColumn+1)

            }else{
                return destRow == curRow+1 || destRow == curRow-1
            }
        }
        return false
    }

    // Knights can only move in an L from their current square.
    private fun canKnightMove(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        return abs(curColumn - destColumn) == 2 && abs(curRow - destRow) == 1 ||
                abs(curColumn - destColumn) == 1 && abs(curRow - destRow) == 2
    }

    // Rooks can only move vertically in the same column or horizontally in the same row as their current square.
    private fun canRookMove(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        return curColumn == destColumn && isGapVertically(curColumn, curRow, destColumn, destRow) ||
                curRow == destRow && isGapHorizontally(curColumn, curRow, destColumn, destRow)
    }


    // Bishops can only move diagonally.
    private fun canBishopMove(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (abs(curColumn - destColumn) == abs(curRow - destRow)) {
            return isGapDiagonally(curColumn, curRow, destColumn, destRow)

        }else{
            return false
        }
    }

    // Queens can move vertically, horizontally and diagonally.
    private fun canQueenMove(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        return canRookMove(curColumn, curRow, destColumn, destRow) || canBishopMove(curColumn, curRow, destColumn, destRow)
    }

    // Kings can only move one square vertically, horizontally or diagonally.
    private fun canKingMove(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (canQueenMove(curColumn, curRow, destColumn, destRow)) {
            return abs(curColumn - destColumn) == 1 && abs(curRow - destRow) == 1 || abs(curColumn - destColumn) + abs(curRow - destRow) == 1
        }else{
            return false
        }
    }



    fun canMove(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (curColumn == destColumn && curRow == destRow) {
            return false
        }

        val movingPiece = pieceAt(curColumn, curRow) ?: return false

        return when(movingPiece.rank) {
            ChessRank.PAWN -> canPawnMove(curColumn, curRow, destColumn, destRow)
            ChessRank.ROOK -> canRookMove(curColumn, curRow, destColumn, destRow)
            ChessRank.KNIGHT -> canKnightMove(curColumn, curRow, destColumn, destRow)
            ChessRank.BISHOP -> canBishopMove(curColumn, curRow, destColumn, destRow)
            ChessRank.QUEEN -> canQueenMove(curColumn, curRow, destColumn, destRow)
            ChessRank.KING -> canKingMove(curColumn, curRow, destColumn, destRow)

        }
    }
//===============================================================================================

    // Returns the rank of the piece at a certain square, if there is a piece on that square
    fun pieceAt(column: Int, row: Int) : ChessPiece? {
        for (piece in piecesSet) {
            if (column == piece.column && row == piece.row) {
                return piece
            }
        }
        return null
    }

    fun movePiece(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int) {
        if (canMove(curColumn, curRow, destColumn, destRow)){
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

        }else{
            return
        }


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





    // Turns the chess board into a printable string
    override fun toString(): String {

        //the String that contains the chessboard
        var boardString = " \n"
        
        // add every row
        for (row in 7 downTo 0) {
            boardString += "$row"
            
            // add every column in a row
            for (column in 0..7) {

                // check if there is a piece on that square
                val piece = pieceAt(column, row)

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