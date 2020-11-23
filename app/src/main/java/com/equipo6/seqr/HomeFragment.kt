package com.equipo6.seqr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.equipo6.seqr.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val db = FirebaseFirestore.getInstance()
    val id = FirebaseAuth.getInstance().currentUser?.uid
    val employee = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInformation()
    }

    private fun getInformation() {
        rlLoadingHome.visibility = View.VISIBLE
        if (id != null) {
            db.collection("users").document(id).get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val id = documentSnapshot.id
                    val name = documentSnapshot.getString("name").toString()
                    val email = documentSnapshot.getString("email").toString()
                    val phone = documentSnapshot.getString("phone_number").toString()
                    val createdAt = documentSnapshot.getDate("created_at")

                    if(!id.isNullOrEmpty() && !name.isNullOrEmpty() && !email.isNullOrEmpty() && !phone.isNullOrEmpty() && createdAt != null) {
                        tvMessage.text = "Bienvenido"
                        tvName.text = "Nombre:\n$name"
                        tvEmail.text = "Email:\n$email"
                        tvPhone.text = "Telefono:\n$phone"

                        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

                        val cal = Calendar.getInstance()
                        cal.time = createdAt

                        tvCreatedAt.text = "Miembro desde:\n${simpleDateFormat.format(createdAt)}"

                        rlLoadingHome.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}