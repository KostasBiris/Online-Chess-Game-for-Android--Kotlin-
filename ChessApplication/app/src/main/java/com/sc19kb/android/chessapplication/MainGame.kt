package com.sc19kb.android.chessapplication

import android.os.Bundle

//const val TAG = "MainGame"

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.PrintWriter
import java.util.concurrent.Executors
//const val TAG = "MainActivity"

class MainGame : AppCompatActivity(), ChessInterface {
    private lateinit var chessBoard: ChessBoard
    private var printWriter: PrintWriter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)
        chessBoard = findViewById<ChessBoard>(R.id.chess_board)
        chessBoard.chessInterface = this
        
    }

    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return ChessBoardConsole.pieceAt(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        Log.d(TAG, "$fromCol,$fromRow,$toCol,$toRow")
        ChessBoardConsole.movePiece(fromCol, fromRow, toCol, toRow)
        chessBoard.invalidate()

        printWriter?.let {
            val moveStr = "$fromCol,$fromRow,$toCol,$toRow"
            Executors.newSingleThreadExecutor().execute {
                it.println(moveStr)
            }
        }
    }
}