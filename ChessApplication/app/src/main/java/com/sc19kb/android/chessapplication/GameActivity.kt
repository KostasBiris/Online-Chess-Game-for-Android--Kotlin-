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

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.system.exitProcess

var isMyMove = isMatchMaker


class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        reset_btn.setOnClickListener {
            reset()
        }
        //Connects to the Realtime Database on Firebase
        FirebaseDatabase.getInstance().reference.child("data").child(matchName).addChildEventListener(object : ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.value
                if(isMyMove){
                    isMyMove = false
                    moveonline(data.toString() , isMyMove)
                }
                else{
                    isMyMove = true
                    moveonline(data.toString() , isMyMove)
                }



            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                reset()
                errorMsg("Game Reset")
            }

        })
    }
    
    //Count the number of times each army
    //has clicked on the button
    private var p1MoveCount = 0
    private var p2MoveCount = 0
    
    
    // Function that runs when button is clicked
    fun clickfun(view:View)
    {
        if(isMyMove) {
            val but = view as Button
            val cellOnline: Int = when (but.id) {
                R.id.test_btn -> 1
                else -> {
                    0
                }

            }
            var playerTurn = false
            Handler().postDelayed({ playerTurn = true }, 600)

            playnow(but, cellOnline)
            updateDatabase(cellOnline)

        }
        else{
            Toast.makeText(this , "Wait for your turn" , Toast.LENGTH_LONG).show()
        }
    }
    private var player1 = ArrayList<Int>()
    private var player2 = ArrayList<Int>()
    private var emptyCells = ArrayList<Int>()
    private var activeUser = 1

    private fun playnow(buttonSelected:Button, currCell:Int)
    {
        buttonSelected.text = "1"
        emptyCells.remove(currCell)
        textView1.text = "Turn : Player 2"
        p1MoveCount += 1
        buttonSelected.setTextColor(Color.parseColor("#EC0C0C"))
        player1.add(currCell)
        emptyCells.add(currCell)

        buttonSelected.isEnabled = true

        checkwinner()
    }

    fun moveonline(data : String , move : Boolean){

        if(move) {
            val buttonselected: Button? = when (data.toInt()) {
                1 -> test_btn
                else -> {
                    reset_btn
                }
            }
            if (buttonselected != null) {
                buttonselected.text = "2"
            }
            textView1.text = "Turn : Player 1"
            p2MoveCount +=1
            if (buttonselected != null) {
                buttonselected.setTextColor(Color.parseColor("#D22BB804"))
            }
            player2.add(data.toInt())
            emptyCells.add(data.toInt())

            if (buttonselected != null) {
                buttonselected.isEnabled = true
            }
            checkwinner()
        }
    }

    private fun updateDatabase(cellId : Int)
    {
        FirebaseDatabase.getInstance().reference.child("data").child(matchName).push().setValue(cellId)
    }

    private fun checkwinner():Int
    {
        if(player1.contains(1) && p1MoveCount == 5) {
            buttonDisable()
            disableReset()

            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 1 Wins!!" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                removeMatch()
                exitProcess(1)

            }
            Handler().postDelayed({ build.show() }, 2000)
            return 1


        }
        else if(player2.contains(1) && p2MoveCount == 5){
            buttonDisable()
            disableReset()

            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 2 Wins!!" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok"){dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, which ->
                removeMatch()
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() } , 2000)
            return 1
        }
        else if(emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(4) && emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7) &&
            emptyCells.contains(8) && emptyCells.contains(9) ) {

            val build = AlertDialog.Builder(this)
            build.setTitle("Game Draw")
            build.setMessage("Nobody Wins" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok"){dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, which ->
                exitProcess(1)
                removeMatch()
            }
            build.show()
            return 1

        }
        return 0
    }

    fun reset()
    {
        player1.clear()
        player2.clear()
        p1MoveCount = 0
        p2MoveCount = 0
        emptyCells.clear()
        activeUser = 1

        val buttonselected = test_btn
        buttonselected.isEnabled = true
        buttonselected.text = ""
        textView2.text = "Player1 : $p1MoveCount"
        textView2.text = "Player2 : $p2MoveCount"
        isMyMove = isMatchMaker
        //startActivity(Intent(this,GameActivity::class.java))
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
