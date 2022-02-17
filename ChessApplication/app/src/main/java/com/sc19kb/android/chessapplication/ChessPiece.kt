package com.sc19kb.android.chessapplication

/*
* -------------- CHESS PIECE -----------------
*
* Data Class containing all the information
* used to describe a certain chess piece
*/

data class ChessPiece(var column: Int, var row: Int, var army: ChessArmy, var rank: ChessRank, var resID: Int)