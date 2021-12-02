package com.sc19kb.android.chessapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private  const val  TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    var chessModel = ChessModel()

    //database instance
    private var database= FirebaseDatabase.getInstance()
    private var myRef=database.reference

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "$chessModel")

//        mAuth = FirebaseAuth.getInstance()
//        val user = mAuth.currentUser
//
//        /**If user is not authenticated, send him to SignInActivity to authenticate first.
//         * Else send him to DashboardActivity*/
//        Handler().postDelayed({
//            if(user != null){
//                val dashboardIntent = Intent(this, DashboardActivity::class.java)
//                startActivity(dashboardIntent)
//                finish()
//            }else{
//                val signInIntent = Intent(this, SignInActivity::class.java)
//                startActivity(signInIntent)
//                finish()
//            }
//        }, 2000)

    }
}