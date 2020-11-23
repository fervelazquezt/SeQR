package com.equipo6.seqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.equipo6.seqr.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val employee = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnRecoverPass.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RecoverPassActivity::class.java
                )
            )
        }
        btnSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        btnLogin.setOnClickListener {
            rlLoadingLogin.visibility = View.VISIBLE
            employee.email = etEmail.editText?.text.toString()
            employee.password = etPassword.editText?.text.toString()
            if(employee.email.isEmpty() && employee.password.isEmpty()) {
                rlLoadingLogin.visibility = View.INVISIBLE
                dialogOnError("Llene los campos")
            } else {
                login()
            }
        }
    }

    fun login() {
        val auth= FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        auth.signInWithEmailAndPassword(employee.email, employee.password)
            .addOnSuccessListener {
                val id = it.user?.uid
                if(!id.isNullOrEmpty()) {
                    val homeIntent = Intent(this, MainActivity::class.java)
                    startActivity(homeIntent)
                    finish()
                }
//                val homeIntent = Intent(this, MainActivity::class.java)
//                startActivity(homeIntent)
//                finish()
            }
            .addOnFailureListener {
                rlLoadingLogin.visibility = View.INVISIBLE
                dialogOnError("Credenciales no validas")
            }
    }

    private fun dialogOnError(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Error")
            .setMessage(message)
            .setNegativeButton("Okey") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setBackground(resources.getDrawable(R.drawable.alert_dialog_bg, null))
            .setCancelable(false)
            .show()
    }
}