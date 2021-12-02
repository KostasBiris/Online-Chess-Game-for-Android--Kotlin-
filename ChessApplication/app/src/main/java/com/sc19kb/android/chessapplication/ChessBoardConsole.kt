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

class ChessBoardConsole {

    var piecesSet = mutableSetOf<ChessPiece>()

    //Clear the chessboardString and re-arrange the pieces
    init {
        reset()
    }

    private fun reset() {
        // Clear the Chessboard before re-arranging the pieces
        piecesSet.removeAll(piecesSet)

        //Kings and Queens are unique pieces, so we arrange them by one
        piecesSet.add(ChessPiece(3, 0, ChessArmy.WHITE, ChessRank.QUEEN))
        piecesSet.add(ChessPiece(3, 7, ChessArmy.BLACK, ChessRank.QUEEN))
        piecesSet.add(ChessPiece(4, 0, ChessArmy.WHITE, ChessRank.KING))
        piecesSet.add(ChessPiece(4, 7, ChessArmy.BLACK, ChessRank.KING))

        // Bishops, Knights and Rooks have come in pairs of 2 so we arrange them by two
        for (i in 0..1) {
            piecesSet.add(ChessPiece(2 + i * 3, 0, ChessArmy.WHITE, ChessRank.BISHOP))
            piecesSet.add(ChessPiece(2 + i * 3, 7, ChessArmy.BLACK, ChessRank.BISHOP))

            piecesSet.add(ChessPiece(1 + i * 5, 0, ChessArmy.WHITE, ChessRank.KNIGHT))
            piecesSet.add(ChessPiece(1 + i * 5, 7, ChessArmy.BLACK, ChessRank.KNIGHT))

            piecesSet.add(ChessPiece(0 + i * 7, 0, ChessArmy.WHITE, ChessRank.ROOK))
            piecesSet.add(ChessPiece(0 + i * 7, 7, ChessArmy.BLACK, ChessRank.ROOK))

        }

        // Pawns come in two groups of 8
        for (i in 0..7) {
            piecesSet.add(ChessPiece(i, 1, ChessArmy.WHITE, ChessRank.PAWN))
            piecesSet.add(ChessPiece(i, 6, ChessArmy.BLACK, ChessRank.PAWN))
        }
    }

    // Returns the rank of the piece at a certain square, if there is a piece on that square
    private fun pieceAt(col: Int, row: Int) : ChessPiece? {
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
                            if (white) "N" else "n"
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