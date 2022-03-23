package com.sc19kb.android.chessapplication

/*
 * ------------------ MAIN GAME --------------------
 *
 * This is where the two users get directed after
 * joining the game match with the same name from the Lobby
 *
 * For now its only functionality is to change the colour
 * and number on a screen button every time the users click on it
 *
 * First user to click on it five (5) times is crowned the winner.
 *
 * Will be replaced by an actual chess game.
 *
 */

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_game.*
import java.io.PrintWriter
import java.util.concurrent.Executors
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), ChessInterface {
    var isMyMove = isMatchMaker
    val database = FirebaseDatabase.getInstance().getReference("Matches")
    private lateinit var chessBoard: ChessBoard
    private var printWriter: PrintWriter? = null


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
                var boardUpdateString: String = data.toString()
                ChessBoardConsole.update(boardUpdateString)

                isMyMove = !isMyMove

            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.value
                println(data)
                var boardUpdateString: String = data.toString()
                ChessBoardConsole.update(boardUpdateString)

                isMyMove = !isMyMove
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                reset()
                errorMsg("Game Reset")
            }
        })
    }
    // ===================================================================
    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return ChessBoardConsole.pieceAt(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        if(isMyMove){
            Log.d(TAG, "$fromCol,$fromRow,$toCol,$toRow")
            ChessBoardConsole.movePiece(fromCol, fromRow, toCol, toRow)
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
    // ===================================================================
    
    // Function that runs when button is clicked
//    fun clickfun(view:View)
//    {
//        if(isMyMove) {
//            val but = view as Button
//            val cellOnline: Int = when (but.id) {
//                R.id.test_btn -> 1
//                else -> {
//                    0
//                }
//
//            }
//            var playerTurn = false
//            Handler().postDelayed({ playerTurn = true }, 600)
//
//            playnow(but, cellOnline)
//            updateDatabase(cellOnline)
//
//        }
//        else{
//            Toast.makeText(this , "Wait for your turn" , Toast.LENGTH_LONG).show()
//        }
//    }


    private fun updateDatabase()
    {
        database.child(matchName).child("Board String").setValue(ChessBoardConsole.moveString)
//        database.child(matchName).child("White EnPassant Flag").setValue(ChessBoardConsole.whiteEnPassantFlag)
//        database.child(matchName).child("Black EnPassant Flag").setValue(ChessBoardConsole.blackEnPassantFlag)
//        database.child(matchName).child("White Right Castle Flag").setValue(ChessBoardConsole.whiteRightCastleFlag)
//        database.child(matchName).child("White Left Castle Flag").setValue(ChessBoardConsole.whiteLeftCastleFlag)
//        database.child(matchName).child("Black Right Castle Flag").setValue(ChessBoardConsole.blackRightCastleFlag)
//        database.child(matchName).child("Black Left Castle Flag").setValue(ChessBoardConsole.blackLeftCastleFlag)
    }

//    private fun updateLocal()
//    {
//        database.child(matchName).get().addOnSuccessListener {
//
//            if (it.exists()){
//                try{
//                    var boardUpdateString: String = it.child("Board String").getValue<String>() as String
//                    ChessBoardConsole.update(boardUpdateString)
//                    ChessBoardConsole.whiteEnPassantFlag = it.child("White EnPassant Flag").getValue<Int>() as Int
//                    ChessBoardConsole.blackEnPassantFlag = it.child("Black EnPassant Flag").getValue<Int>() as Int
//                    ChessBoardConsole.whiteRightCastleFlag = it.child("White Right Castle Flag").getValue<Boolean>() as Boolean
//                    ChessBoardConsole.whiteLeftCastleFlag = it.child("White Left Castle Flag").getValue<Boolean>() as Boolean
//                    ChessBoardConsole.blackRightCastleFlag = it.child("Black Right Castle Flag").getValue<Boolean>() as Boolean
//                    ChessBoardConsole.blackLeftCastleFlag = it.child("Black Left Castle Flag").getValue<Boolean>() as Boolean
//                }catch (e: NullPointerException){
//                    println("-0-0-0-0-0---Null---0-0-0-0-0-")
//                }
//
//            }else{
//                Toast.makeText(this,"MATCH NOT FOUND ",Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener{
//            Toast.makeText(this,"FAILED",Toast.LENGTH_SHORT).show()
//        }
//    }



//    private fun checkwinner():Int
//    {
//        if(player1.contains(1) && p1MoveCount == 5) {
//            buttonDisable()
//            disableReset()
//
//            val build = AlertDialog.Builder(this)
//            build.setTitle("Game Over")
//            build.setMessage("Player 1 Wins!!" + "\n\n" + "Do you want to play again")
//            build.setPositiveButton("Ok") { dialog, which ->
//                reset()
//            }
//            build.setNegativeButton("Exit") { dialog, which ->
//                removeMatch()
//                exitProcess(1)
//
//            }
//            Handler().postDelayed({ build.show() }, 2000)
//            return 1
//
//
//        }
//        else if(player2.contains(1) && p2MoveCount == 5){
//            buttonDisable()
//            disableReset()
//
//            val build = AlertDialog.Builder(this)
//            build.setTitle("Game Over")
//            build.setMessage("Player 2 Wins!!" + "\n\n" + "Do you want to play again")
//            build.setPositiveButton("Ok"){dialog, which ->
//                reset()
//            }
//            build.setNegativeButton("Exit"){dialog, which ->
//                removeMatch()
//                exitProcess(1)
//            }
//            Handler().postDelayed(Runnable { build.show() } , 2000)
//            return 1
//        }
//        else if(emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(4) && emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7) &&
//            emptyCells.contains(8) && emptyCells.contains(9) ) {
//
//            val build = AlertDialog.Builder(this)
//            build.setTitle("Game Draw")
//            build.setMessage("Nobody Wins" + "\n\n" + "Do you want to play again")
//            build.setPositiveButton("Ok"){dialog, which ->
//                reset()
//            }
//            build.setNegativeButton("Exit"){dialog, which ->
//                exitProcess(1)
//                removeMatch()
//            }
//            build.show()
//            return 1
//
//        }
//        return 0
//    }

    fun reset()
    {
        ChessBoardConsole.reset()

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

    private fun disableReset()
    {
        reset_btn.isEnabled = false
        Handler().postDelayed({ reset_btn.isEnabled = true }, 2200)
    }

    override fun onBackPressed() {
        removeMatch()
        if(isMatchMaker){
            FirebaseDatabase.getInstance().reference.child("data").child(matchName).removeValue()
        }
        exitProcess(0)
    }
}
