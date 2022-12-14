package com.sc19kb.android.chessapplication

/*
 * ------------------ ONLINE GAME --------------------
 *
 * This is where the two users get directed after
 * joining the game match with the same name from the Lobby
 *
 * It makes use of the Chess Interface in order to utilise
 * Chess Core and Chess Board and use them at once to realise
 * the Online Chess Game.
 *
 * It also maintains communication with the Firebase Realtime Database
 * in order to post and retrieve moveStrings from either/both users
 *
 */

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_game.*
import java.io.PrintWriter
import java.util.concurrent.Executors

class OnlineGameActivity : AppCompatActivity(), ChessInterface {
    var isMyMove = isMatchMaker
    var enemyBoardString: String = ""

    val database = FirebaseDatabase.getInstance().getReference("Matches")
    private lateinit var chessBoard: ChessBoard
    private var printWriter: PrintWriter? = null
    var chessModel = ChessCore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)
        chessBoard = findViewById<ChessBoard>(R.id.chess_board)
        chessBoard.chessInterface = this

        //Connects to the Realtime Database on Firebase
        database.child(matchName).addChildEventListener(object : ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                var data = snapshot.value
                println(data)
                var moveUpdateString: String = data.toString()
                if (moveUpdateString == "White Forfeited" || moveUpdateString == "Black Forfeited") {
                    if (moveUpdateString == "Black Forfeited") endMatch(ChessArmy.WHITE, true)
                    else endMatch(ChessArmy.BLACK, true)

                }else{
                    ChessCore.update(moveUpdateString)
                    if (chessModel.blackCheckMated) endMatch(ChessArmy.WHITE)
                    else if (chessModel.whiteCheckMated) endMatch(ChessArmy.BLACK)
                }
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var data = snapshot.value
                println(data)
                var moveUpdateString: String = data.toString()
                if (moveUpdateString == "White Forfeited" || moveUpdateString == "Black Forfeited") {
                    if (moveUpdateString == "Black Forfeited") endMatch(ChessArmy.WHITE, true)
                    else endMatch(ChessArmy.BLACK, true)
                }else {
                    ChessCore.update(moveUpdateString)
                    if (chessModel.blackCheckMated) endMatch(ChessArmy.WHITE)
                    else if (chessModel.whiteCheckMated) endMatch(ChessArmy.BLACK)
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                reset()
                errorMsg("Game Reset")
            }
        })
    }

    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return ChessCore.pieceAt(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        if( (isMatchMaker && ChessCore.round == ChessArmy.WHITE) || (!isMatchMaker && ChessCore.round == ChessArmy.BLACK) ){
            Log.d(TAG, "$fromCol,$fromRow,$toCol,$toRow")
            ChessCore.movePiece(fromCol, fromRow, toCol, toRow)
            chessBoard.invalidate()

            printWriter?.let {
                val moveStr = "$fromCol,$fromRow,$toCol,$toRow"
                Executors.newSingleThreadExecutor().execute {
                    it.println(moveStr)
                }
            }
            updateDatabase()
        }else{
            Toast.makeText(this , "Wait for your turn" , Toast.LENGTH_LONG).show()
        }

    }

    // Sends the Move String to the Database
    private fun updateDatabase()
    {
        database.child(matchName).child("Move String").setValue(ChessCore.moveString)
        isMyMove = !isMyMove
    }

    // Resets the Chessboard to it's original form
    fun reset()
    {
        ChessCore.reset()

        if(isMatchMaker){
            FirebaseDatabase.getInstance().reference.child("data").child(matchName).removeValue()
        }
    }

    private fun buttonDisable()
    {
        val buttonSelected = test_btn
        if(buttonSelected.isEnabled == true)
            buttonSelected.isEnabled = false
    }

    private fun removeMatch(){
        if(isMatchMaker){
            FirebaseDatabase.getInstance().reference.child("matches").child(keyValue).removeValue()
        }
    }

    fun errorMsg(value : String){
        Toast.makeText(this , value  , Toast.LENGTH_SHORT).show()
    }

    // Ends the Match and Declares the Winner
    fun endMatch(winner: ChessArmy, forfeited: Boolean = false){
        val builder = AlertDialog.Builder(this)

        // Win because of Enemy Player Forfeiting
        if (forfeited){
            builder.setTitle("Forfeited!")
            if(winner == ChessArmy.WHITE) builder.setMessage("White wins!")
            else if (winner == ChessArmy.BLACK) builder.setMessage("Black wins!")

            builder.setPositiveButton("Leave"){dialogInterface: DialogInterface, i:Int ->
                startActivity(Intent(this , DashboardActivity::class.java))
                chessModel.reset()
                chessModel.round = ChessArmy.WHITE
                finish()
            }
            builder.show()
        }

        // Win because of Checkmate
        else {
            builder.setTitle("Checkmate!")
            if(winner == ChessArmy.WHITE) builder.setMessage("White wins!")
            else if (winner == ChessArmy.BLACK) builder.setMessage("Black wins!")
            else builder.setMessage("Error. Winner Indecisive")
            builder.setPositiveButton("Rematch") { dialogInterface: DialogInterface, i: Int ->
                startActivity(Intent(this , OnlineGameActivity::class.java))
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
    }

    // Leaving the game activity makes the player forfeit the match
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

    // Lets the forfeiting user know that they have lost due to leaving the match
    fun forfeitMatch() {

        if (isMatchMaker) ChessCore.moveString = "White Forfeited"
        else ChessCore.moveString = "Black Forfeited"

        updateDatabase()
    }
}
