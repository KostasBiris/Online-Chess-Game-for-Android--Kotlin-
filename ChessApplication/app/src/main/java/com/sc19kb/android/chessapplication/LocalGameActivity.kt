package com.sc19kb.android.chessapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class LocalGameActivity : AppCompatActivity(),
    ChessInterface {

    var chessModel = ChessBoardConsole

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
        if (chessModel.blackCheckMated) endMatch(ChessArmy.WHITE)
        else if (chessModel.whiteCheckMated) endMatch(ChessArmy.BLACK)
    }



    fun endMatch(winner: ChessArmy){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Checkmate!")
        if(winner == ChessArmy.WHITE) builder.setMessage("White wins!")
        else if (winner == ChessArmy.BLACK) builder.setMessage("Black wins!")
        else builder.setMessage("Error. Winner Indecisive")
        builder.setPositiveButton("Rematch") { dialogInterface: DialogInterface, i: Int ->
            startActivity(Intent(this , LocalGameActivity::class.java))
            chessModel.reset()
            chessModel.round = ChessArmy.WHITE
            finish()
        }
        builder.setNegativeButton("Leave"){dialogInterface: DialogInterface, i:Int ->
            startActivity(Intent(this , DashboardActivity::class.java))
            chessModel.reset()
            chessModel.round = ChessArmy.WHITE
            finish()
        }
        builder.show()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Forfeit")
        builder.setMessage("Do you want to forfeit?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            forfeitMatch()
        }
        builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }

    fun forfeitMatch() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Forfeited")
        if(ChessBoardConsole.round == ChessArmy.WHITE) builder.setMessage("Black wins!")
        else builder.setMessage("White wins!")
        builder.setPositiveButton("Rematch") {dialogInterface: DialogInterface, i: Int ->
            chessModel.reset()
            chessModel.round = ChessArmy.WHITE
            startActivity(Intent(this , LocalGameActivity::class.java))
            finish()
        }
        builder.setNegativeButton("Leave"){dialogInterface: DialogInterface, i:Int ->
            chessModel.reset()
            chessModel.round = ChessArmy.WHITE
            startActivity(Intent(this , DashboardActivity::class.java))
            finish()
        }
        builder.show()
    }
}