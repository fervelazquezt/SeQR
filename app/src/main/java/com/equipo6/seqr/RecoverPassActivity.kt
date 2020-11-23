package com.equipo6.seqr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_recover_pass.*

class RecoverPassActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_pass)
        btnSendRecovery.setOnClickListener {
            val email = etRecoverPassowordEmail.editText?.text.toString()
            rlLoadingRecoveryPassword.visibility = View.VISIBLE
            if(email.isNotEmpty()) {
                sendEmailToChangePassword(email)
                etRecoverPassowordEmail.error = null
            } else {
                rlLoadingRecoveryPassword.visibility = View.INVISIBLE

                etRecoverPassowordEmail.error = getString(R.string.error)
            }
        }
        btnCancelRecovery.setOnClickListener { finish() }
    }

    private fun sendEmailToChangePassword(email: String) {
        auth.setLanguageCode("es")
        auth.sendPasswordResetEmail(email).addOnCompleteListener {task ->
            if(task.isSuccessful) {
                rlLoadingRecoveryPassword.visibility = View.INVISIBLE
                dialogOnSuccessful()
            } else {
                rlLoadingRecoveryPassword.visibility = View.INVISIBLE
                dialogOnError("No sea ha podido enviar el correo a esta direccion")
            }
        }
    }

    private fun dialogOnSuccessful() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirmacion")
            .setMessage("El email ha sido enviado")
            .setPositiveButton("Okey") { dialogInterface, _ ->
                dialogInterface.dismiss()
                startActivity(
                    Intent(this, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                ) //Flags delete all the previous activities open
                finish()
            }
            .setBackground(resources.getDrawable(R.drawable.alert_dialog_bg, null))
            .setCancelable(false)
            .show()
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