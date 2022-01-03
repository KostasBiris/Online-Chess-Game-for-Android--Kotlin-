package com.sc19kb.android.chessapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//const val TAG = "MainGame"

class MainGame : AppCompatActivity(),
    ChessInterface {

    var chessModel = ChessBoardConsole()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)

        findViewById<ChessBoard>(R.id.chess_board).chessInterface = this
    }

    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return chessModel.pieceAt(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        chessModel.movePiece(fromCol, fromRow, toCol, toRow)
        findViewById<ChessBoard>(R.id.chess_board).invalidate()
    }
}