package com.sc19kb.android.chessapplication

/*
 * ------------------ MATCH LOBBY --------------------
 *
 * The user gets directed here after pressing "Play Online"
 * on the Dashboard.
 *
 * To Create a new chess match, they type in the name they want
 * to give to the match and press on "Create".
 *
 * To Join an existing match, they type in the name of the match
 * and then press on "Join" to join a match created by someone else.
 *
 */


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_lobby.*

var isMatchMaker = true
var matchName = "null"
var matchNameFound = false
var checkTemp = true
var keyValue:String = "null"

class LobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)
        val database = FirebaseDatabase.getInstance().getReference("Matches")

        //======================== CREATE MATCH : [Start] =============================

        Create.setOnClickListener{
            matchName = "null"
            matchNameFound = false
            checkTemp = true
            keyValue= "null"
            matchName = MatchName.text.toString()
            Create.visibility = View.GONE
            Join.visibility = View.GONE
            MatchName.visibility = View.GONE
            textView4.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

            if(matchName != "null" && matchName != "") {
                isMatchMaker = true
                //Adds the new game match on the "matches" table in the database
                database.addValueEventListener(object  :ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        //Check if a match with the same name exists
                        val check = isValueAvailable(snapshot , matchName)

                        Handler().postDelayed({
                            if(check) {
                                Create.visibility = View.VISIBLE
                                Join.visibility = View.VISIBLE
                                MatchName.visibility = View.VISIBLE
                                textView4.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE

                            }

                            //Create new match and wait for the game to begin
                            else{
                                database.push().setValue(matchName)
                                isValueAvailable(snapshot,matchName)
                                checkTemp = false
                                Handler().postDelayed({
                                    accepted()
                                    errorMsg("Please don't go back")
                                } , 300)
                            }
                        }, 2000)
                    }
                })
            // Executes if a wrong match name is entered
            } else {
                Create.visibility = View.VISIBLE
                Join.visibility = View.VISIBLE
                MatchName.visibility = View.VISIBLE
                textView4.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                errorMsg("Enter Match Name Properly")
            }
        }
        //=========================== CREATE MATCH : [End] ================================


        //=========================== JOIN MATCH : [Start] =============================

        Join.setOnClickListener{
            matchName = "null"
            matchNameFound = false
            checkTemp = true
            keyValue= "null"
            matchName = MatchName.text.toString()

            //find match
            if(matchName != "null" && !matchName.contains(" ")) {
                Create.visibility = View.GONE
                Join.visibility = View.GONE
                MatchName.visibility = View.GONE
                textView4.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                isMatchMaker = false
                database.addValueEventListener(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val data:Boolean = isValueAvailable(snapshot , matchName)

                        Handler().postDelayed({
                            if(data) {
                                matchNameFound = true
                                accepted()
                            } else{
                                Create.visibility = View.VISIBLE
                                Join.visibility = View.VISIBLE
                                MatchName.visibility = View.VISIBLE
                                textView4.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                                errorMsg("Invalid Match Name")
                            }
                        } , 2000)
                    }
                })
            } else errorMsg("Enter Match Name Properly")
        }
        //=========================== JOIN MATCH : [End] =============================
    }

    fun accepted() {
        startActivity(Intent(this, OnlineGameActivity::class.java))
        finish()
    }

    fun errorMsg(value : String){ Toast.makeText(this , value  , Toast.LENGTH_SHORT).show() }

    fun isValueAvailable(snapshot: DataSnapshot , matchName : String): Boolean {
        val data = snapshot.children
        data.forEach{
            val value = it.value.toString()
            if(value == matchName) {
                keyValue = it.key.toString()
                return true
            }
        }
        return false
    }

    override fun onBackPressed() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}
