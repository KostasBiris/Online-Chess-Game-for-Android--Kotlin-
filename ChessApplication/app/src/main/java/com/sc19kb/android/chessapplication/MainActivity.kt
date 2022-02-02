package com.sc19kb.android.chessapplication

/*
*----------- MAIN ACTIVITY ----------
*
* Contains the splash screen that navigates
* the user to the Sign-In Activity and
* from there to the Dashboard Activity
*/


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

const val  TAG = "MainActivity"

class MainActivity : AppCompatActivity(){

    //database instance
    private var database= FirebaseDatabase.getInstance()
    private var myRef=database.reference

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

////==============================================================================
//        Log.d(TAG, "$chessModel")
//
//        val MainGameIntent = Intent(this, MainGame::class.java)
//        startActivity(MainGameIntent)
//        finish()
////============================================================================

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        /**If user is not authenticated, send him to SignInActivity to authenticate first.
         * Else send him to DashboardActivity*/
        Handler().postDelayed({
            if(user != null){
                val dashboardIntent = Intent(this, DashboardActivity::class.java)
                startActivity(dashboardIntent)
                finish()
            }else{
                val signInIntent = Intent(this, SignInActivity::class.java)
                startActivity(signInIntent)
                finish()
            }
        }, 2000)

    }

}