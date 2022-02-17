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
import java.lang.Math.abs


object ChessBoardConsole {

    var round:ChessArmy = ChessArmy.WHITE

    //Special move flags
    var whiteEnPassantFlag: Int = -1
    var blackEnPassantFlag: Int = -1
    var whiteRightCastleFlag: Boolean = true
    var whiteLeftCastleFlag: Boolean = true
    var blackRightCastleFlag: Boolean = true
    var blackLeftCastleFlag: Boolean = true


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

    // En-passant is a move executed by pawns, where they capture the enemy pawn by going behind it.
    fun enPassant(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean{
        if (army==ChessArmy.WHITE && blackEnPassantFlag!=destColumn || army==ChessArmy.BLACK && whiteEnPassantFlag!=destColumn) return false
        if ((curRow != 4 && army == ChessArmy.WHITE) || (curRow != 3 && army == ChessArmy.BLACK)) return false
        if (pieceAt(destColumn, destRow) == null) {
            var enemyPiece = pieceAt(destColumn, destRow - 1)
            if (army == ChessArmy.BLACK) enemyPiece = pieceAt(destColumn, destRow + 1)
            if (abs(destColumn - curColumn) == 1 && enemyPiece != null) {
                if (enemyPiece.army == army) return false
                if (enemyPiece.rank != ChessRank.PAWN) return false

                piecesSet.remove(enemyPiece)

                return true
            }
            return false
        }
        return false
    }

    fun Castle(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int, leftCastle: Boolean): Boolean{
        piecesSet.remove(pieceAt(curColumn, curRow))
        piecesSet.remove(pieceAt(destColumn, destRow))
        // Left Castle
        if (leftCastle) {
            if(army==ChessArmy.WHITE) {
                piecesSet.add(ChessPiece(2, 0, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
                piecesSet.add(ChessPiece(3, 0, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))

            }else{
                piecesSet.add(ChessPiece(2, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
                piecesSet.add(ChessPiece(3, 7, ChessArmy.BLACK, ChessRank.ROOK, R.drawable.rook_black))
            }
        // Right Castle
        }else{
            if(army==ChessArmy.WHITE) {
                piecesSet.add(ChessPiece(6, 0, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
                piecesSet.add(ChessPiece(5, 0, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))

            }else{
                piecesSet.add(ChessPiece(6, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
                piecesSet.add(ChessPiece(5, 7, ChessArmy.BLACK, ChessRank.ROOK, R.drawable.rook_black))
            }
        }
        return true
    }






    // Pawns can only move one Square forward but are able to move two Squares on their first move.
    fun canPawnMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (curColumn == destColumn) {
           // val p = pieceAt(curColumn, curRow)
            if (curRow == 1 && army == ChessArmy.WHITE && pieceAt(curColumn, destRow) == null) { // First move of a White pawn.
                if(destRow == 3) whiteEnPassantFlag = destColumn //passantable
                return destRow == 2 || destRow == 3
            }
            if (curRow == 6 && army == ChessArmy.BLACK && pieceAt(curColumn, destRow) == null) { // First move of a Black pawn.
                if(destRow == 4) blackEnPassantFlag = destColumn //passantable
                return destRow == 5 || destRow == 4
            }
            if ((army == ChessArmy.WHITE && destRow == curRow+1 && pieceAt(destColumn,destRow) == null) //move one square forward.
                 || (army == ChessArmy.BLACK && destRow == curRow-1) && pieceAt(destColumn,destRow) == null) return true

        // Captures with White pawn.
        }else if (army == ChessArmy.WHITE && abs(destColumn - curColumn) ==1 && destRow == curRow+1) {
            if (pieceAt(destColumn, destRow) != null) return true // Simple diagonal capture
            else if (enPassant(army, curColumn, curRow, destColumn, destRow)) return true // En-passant

        // Captures with Black pawn.
        }else if(army == ChessArmy.BLACK && abs(destColumn - curColumn) ==1 && destRow == curRow-1) {
            if (pieceAt(destColumn, destRow) != null) return true // Simple diagonal capture
            else if (enPassant(army, curColumn, curRow, destColumn, destRow)) return true // En-passant
        }
        return false
    }

    // Rooks can only move vertically in the same column or horizontally in the same row as their current square.
    private fun canRookMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        var y:Boolean =  curColumn == destColumn && isGapVertically(curColumn, curRow, destColumn, destRow) ||
                curRow == destRow && isGapHorizontally(curColumn, curRow, destColumn, destRow)
        if(y && army == ChessArmy.WHITE && curColumn == 0 && curRow==0) whiteLeftCastleFlag = false
        if(y && army == ChessArmy.WHITE && curColumn == 7 && curRow==0) whiteRightCastleFlag = false
        if(y && army == ChessArmy.BLACK && curColumn == 0 && curRow==7) blackLeftCastleFlag = false
        if(y && army == ChessArmy.BLACK && curColumn == 7 && curRow==7) blackRightCastleFlag = false
        return y
    }

    // Knights can only move in an L from their current square.
    private fun canKnightMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        return abs(curColumn - destColumn) == 2 && abs(curRow - destRow) == 1 ||
                abs(curColumn - destColumn) == 1 && abs(curRow - destRow) == 2
    }

    // Bishops can only move diagonally.
    private fun canBishopMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (abs(curColumn - destColumn) == abs(curRow - destRow)) {
            return isGapDiagonally(curColumn, curRow, destColumn, destRow)

        }else{
            return false
        }
    }

    // Queens can move vertically, horizontally and diagonally.
    private fun canQueenMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        return canRookMove(army, curColumn, curRow, destColumn, destRow) || canBishopMove(army, curColumn, curRow, destColumn, destRow)
    }

    // Kings can only move one square vertically, horizontally or diagonally.
    private fun canKingMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
     //white left castling
        if (army == ChessArmy.WHITE && whiteLeftCastleFlag){
            if (destColumn == 0 && destRow == 0 && isGapHorizontally(curColumn, curRow, destColumn, destRow)){
                Castle(army, curColumn, curRow, destColumn, destRow, true)
                whiteLeftCastleFlag = false
                whiteRightCastleFlag = false
                return false
            }
        }
        if (army == ChessArmy.WHITE && whiteRightCastleFlag){
            if (destColumn == 7 && destRow == 0 && isGapHorizontally(curColumn, curRow, destColumn, destRow)){
                Castle(army, curColumn, curRow, destColumn, destRow, false)
                whiteRightCastleFlag = false
                whiteLeftCastleFlag = false
                return false
            }
        }
        if (army == ChessArmy.BLACK && blackLeftCastleFlag){
            if (destColumn == 0 && destRow == 7 && isGapHorizontally(curColumn, curRow, destColumn, destRow)){
                Castle(army, curColumn, curRow, destColumn, destRow, true)
                blackLeftCastleFlag = false
                blackRightCastleFlag = false
                return false
            }

        }
        if (army == ChessArmy.BLACK && blackRightCastleFlag){
            if (destColumn == 7 && destRow == 7 && isGapHorizontally(curColumn, curRow, destColumn, destRow)){
                Castle(army, curColumn, curRow, destColumn, destRow, false)
                blackRightCastleFlag = false
                blackLeftCastleFlag = false
                return false
            }

        }

        if (canQueenMove(army, curColumn, curRow, destColumn, destRow)) {
            return abs(curColumn - destColumn) == 1 && abs(curRow - destRow) == 1 || abs(curColumn - destColumn) + abs(curRow - destRow) == 1
        }else{
            return false
        }
    }


    fun canMove(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {

        if (curColumn == destColumn && curRow == destRow) {
            return false
        }

        val movingPiece = pieceAt(curColumn, curRow)
        if (movingPiece == null) return false
        if (movingPiece.army != round) return false

        return when(movingPiece.rank) {
            ChessRank.PAWN -> canPawnMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
            ChessRank.ROOK -> canRookMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
            ChessRank.KNIGHT -> canKnightMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
            ChessRank.BISHOP -> canBishopMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
            ChessRank.QUEEN -> canQueenMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
            ChessRank.KING -> canKingMove(movingPiece.army, curColumn, curRow, destColumn, destRow)

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

            // Change Round
            if(round == ChessArmy.WHITE) {
                round = ChessArmy.BLACK
                blackEnPassantFlag = -1
            }
            else if (round == ChessArmy.BLACK) {
                round = ChessArmy.WHITE
                whiteEnPassantFlag = -1
            }

        }else {return}
    }

    fun reset() {
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
        var boardString = "\n"
        
        // add every row
        for (row in 7 downTo 0) {
//            boardString += "$row"
            
            // add every column in a row
            for (column in 0..7) {

                // check if there is a piece on that square
                val piece = pieceAt(column, row)

                // if there is no piece put a dot to indicate the empty square
                if (piece == null) {
                    boardString += "."

                // if there is a piece mark it according to its rank and colour
                // WHITES are CAPITAL case  ,  BLACKS are Lower case
                } else {
                    val isWhite = piece.army == ChessArmy.WHITE
                    boardString += when (piece.rank) {
                        ChessRank.KING -> {
                            if (isWhite) "K" else "k"
                        }
                        ChessRank.QUEEN -> {
                            if (isWhite) "Q" else "q"
                        }
                        ChessRank.BISHOP -> {
                            if (isWhite) "B" else "b"
                        }
                        ChessRank.ROOK -> {
                            if (isWhite) "R" else "r"
                        }
                        ChessRank.KNIGHT -> {
                            if (isWhite) "H" else "h"
                        }
                        ChessRank.PAWN -> {
                            if (isWhite) "P" else "p"
                        }
                    }
                }
            }
        }

        return boardString
    }


    fun update( boardString: String){

        var index: Int = 0
        // add every row
        for (row in 7 downTo 0) {

            // add every column in a row
            for (column in 0..7) {

                var pieceChar: Char = boardString[index]

                if ( pieceChar == '.') break

                var piece: ChessPiece = ChessPiece(-1,-1, ChessArmy.WHITE, ChessRank.PAWN, R.drawable.pawn_white)

                var isWhite: Boolean = pieceChar.isUpperCase()
                var army = if (isWhite) ChessArmy.WHITE else ChessArmy.BLACK

                when (pieceChar) {
                    'K', 'k' -> {
                        piece = if (isWhite) ChessPiece(column, row, army, ChessRank.KING, R.drawable.king_white)
                        else ChessPiece(column, row, army, ChessRank.KING, R.drawable.king_black)

                    }
                    'Q', 'q' -> {
                        piece = if (isWhite) ChessPiece(column, row, army, ChessRank.QUEEN, R.drawable.queen_white)
                        else ChessPiece(column, row, army, ChessRank.QUEEN, R.drawable.queen_black)

                    }
                    'B', 'b' -> {
                        piece = if (isWhite) ChessPiece(column, row, army, ChessRank.BISHOP, R.drawable.bishop_white)
                        else ChessPiece(column, row, army, ChessRank.BISHOP, R.drawable.bishop_black)

                    }
                    'H', 'h' -> {
                        piece = if (isWhite) ChessPiece(column, row, army, ChessRank.KNIGHT, R.drawable.knight_white)
                        else ChessPiece(column, row, army, ChessRank.KNIGHT, R.drawable.knight_black)

                    }
                    'R', 'r' -> {
                        piece = if (isWhite) ChessPiece(column, row, army, ChessRank.ROOK, R.drawable.rook_white)
                        else ChessPiece(column, row, army, ChessRank.ROOK, R.drawable.rook_black)

                    }
                    'P', 'p' -> {
                        piece = if (isWhite) ChessPiece(column, row, army, ChessRank.PAWN, R.drawable.pawn_white)
                        else ChessPiece(column, row, army, ChessRank.PAWN, R.drawable.pawn_black)

                    }
                }

                if (pieceAt(column,row) != null){
                    if (pieceAt(column,row) == piece) break
                    piecesSet.remove(pieceAt(column, row))
                    piecesSet.add(piece)

                }else piecesSet.add(piece)

            }
        }
    }

}