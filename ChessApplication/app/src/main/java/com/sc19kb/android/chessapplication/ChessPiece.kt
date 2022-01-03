package com.sc19kb.android.chessapplication

/*
* -------------- CHESS PIECE -----------------
*
* Data Class containing all the information
* used to describe a certain chess piece
*/

data class ChessPiece(var col: Int, var row: Int, val army: ChessArmy, val rank: ChessRank, val resID: Int)