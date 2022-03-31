package com.sc19kb.android.chessapplication

/*
*------------ DASHBOARD -------------
*
* Users come here after Signing-in
* in order to take a look at their profiles
* and move on to the Chess Game ( local or online )
*
*/


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        // User Details:
        // Id
        id_txt.text = currentUser?.uid
        // Name
        name_txt.text = currentUser?.displayName
        // Email
        email_txt.text = currentUser?.email


        // Play Chess Online
        play_online_btn.setOnClickListener {
            startActivity(Intent(this , LobbyActivity::class.java))
            finish()
        }

        // Play Chess Locally
        play_local_btn.setOnClickListener {
            startActivity(Intent(this , LocalGameActivity::class.java))
            finish()
        }

        // Sign-Out
        sign_out_btn.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}