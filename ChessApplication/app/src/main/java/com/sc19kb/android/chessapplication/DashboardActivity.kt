package com.sc19kb.android.chessapplication

/*
*------------ DASHBOARD -------------
*
* Users come here after Signing-in
* in order to look at their profiles
* and move the chess game
*/


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        // User Details
        // Id
        id_txt.text = currentUser?.uid
        // Name
        name_txt.text = currentUser?.displayName
        // Email
        email_txt.text = currentUser?.email

        // Sign-Out
        sign_out_btn.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Play Chess
        play_btn.setOnClickListener {
            //startActivity(Intent(this , LobbyActivity::class.java))

            //val MainGameIntent = Intent(this, MainGame::class.java)
            startActivity(Intent(this , MainGame::class.java))
            finish()
        }



    }
}