package com.equipo6.seqr

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.CaptureActivity
import kotlinx.android.synthetic.main.fragment_tour.*
import kotlin.math.log


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TourFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TourFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var area: String? = null
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance().currentUser

//    lateinit var etCodigo: EditText
//    lateinit var btnStartScanner: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_tour, container, false)

//        etCodigo = view.findViewById(R.id.etCode)
//        btnStartScanner = view.findViewById(R.id.btnStartScanner)

        view.findViewById<Button>(R.id.btnStartScanner).setOnClickListener {
            startScanner()
        }

        return view
    }

    fun startScanner() {
        val intent = IntentIntegrator.forSupportFragment(this)
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        intent.setPrompt("Escanear codigo")
        intent.setCameraId(0)
        intent.setBeepEnabled(false)
        intent.setBarcodeImageEnabled(false)
        intent.setOrientationLocked(false)
        intent.captureActivity = CaptureActivityPortrait::class.java
        intent.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null) {
            if(result.contents == null) {
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
            } else {
                val email = auth?.email
                if(email != null) {
                    val data = hashMapOf(
                        "user" to email,
                        "check_in" to FieldValue.serverTimestamp(),
                        "checkpoint" to result.contents.toString()
                    )

                    db.collection("tours").add(data)
                        .addOnSuccessListener { result ->
                            dialogOnSuccessful()
                        }
                        .addOnFailureListener {
                            dialogOnError("No se pudo registrar en el historial")
                        }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun dialogOnSuccessful() {
        MaterialAlertDialogBuilder(context)
            .setTitle("Confirmacion")
            .setMessage("Checkpoint visitado")
            .setPositiveButton("Okey") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setBackground(resources.getDrawable(R.drawable.alert_dialog_bg, null))
            .setCancelable(false)
            .show()
    }
    private fun dialogOnError(message: String) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Error")
            .setMessage(message)
            .setNegativeButton("Okey") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setBackground(resources.getDrawable(R.drawable.alert_dialog_bg, null))
            .setCancelable(false)
            .show()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TourFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TourFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}