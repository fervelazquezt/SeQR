package com.equipo6.seqr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.equipo6.seqr.models.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btnConfirmSignup.setOnClickListener {
            rlLoadingSignup.visibility = View.VISIBLE
            val employee = User()
            employee.name = etSignupName.editText?.text.toString()
            employee.phoneNumber = etSignupPhone.editText?.text.toString()
            employee.email = etSignupEmail.editText?.text.toString()
            employee.password = etSignupPassword.editText?.text.toString()
            val code = etSignupCode.editText?.text.toString()

            if(employee.name.isEmpty()) {
                etSignupName.error = getString(R.string.error)
            } else {
                etSignupName.error = null
            }

            if(employee.phoneNumber.isEmpty()) {
                etSignupPhone.error = getString(R.string.error)
            } else {
                etSignupPhone.error = null
            }

            if(employee.email.isEmpty()) {
                etSignupEmail.error = getString(R.string.error)
            } else {
                etSignupEmail.error = null
            }

            if(employee.password.isEmpty()) {
                etSignupPassword.error = getString(R.string.error)
            } else {
                etSignupPassword.error = null
            }

            if(code.isEmpty()) {
                etSignupCode.error = getString(R.string.error)
            } else {
                etSignupCode.error = null
            }

            if(employee.name.isNotEmpty() && employee.phoneNumber.isNotEmpty() && employee.email.isNotEmpty() && employee.password.isNotEmpty() && code.isNotEmpty()) {
                if(employee.password.length >= 6) {
                    if(code == "123456") {
                        registerUser(employee)
                    } else {
                        etSignupCode.error = "Codigo incorrecto, consultelo con su superior"
                        rlLoadingSignup.visibility = View.INVISIBLE
                    }
                } else {
                    etSignupPassword.error = "De contener al menos 6 caracteres"
                    rlLoadingSignup.visibility = View.INVISIBLE
                }
            } else {
                rlLoadingSignup.visibility = View.INVISIBLE
            }
        }
        btnCancelSignup.setOnClickListener { finish() }
    }

    private fun registerUser(user: User) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                val db = FirebaseFirestore.getInstance()
                val docData = hashMapOf(
                    "name" to user.name,
                    "phone_number" to user.phoneNumber,
                    "email" to user.email,
                    "created_at" to Timestamp(Date())
                )

                val id = auth.currentUser?.uid

                if (id != null) {
                    db.collection("users").document(id).set(docData)
                        .addOnSuccessListener {
                            rlLoadingSignup.visibility = View.INVISIBLE
                            startActivity(
                                Intent(this, MainActivity::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            ) //Flags delete all the previous activities open
                            finish()
                        }
                        .addOnFailureListener {
                            rlLoadingSignup.visibility = View.INVISIBLE
                            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                }
            }

            .addOnFailureListener {
                rlLoadingSignup.visibility = View.INVISIBLE
                Toast.makeText(this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show()
            }
    }
}