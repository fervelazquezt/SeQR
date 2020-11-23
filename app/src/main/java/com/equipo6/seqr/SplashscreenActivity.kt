package com.equipo6.seqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splashscreen.*

class SplashscreenActivity : AppCompatActivity() {
    lateinit var destiny: Intent
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val animation = AnimationUtils.loadAnimation(this, R.anim.animation_splash)
        ivLogoSeQR.startAnimation(animation)

        if (auth.currentUser != null) {
            destiny = Intent(this, MainActivity::class.java)
        } else {
            destiny = Intent(this, LoginActivity::class.java)
        }

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(destiny)
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })
    }


}