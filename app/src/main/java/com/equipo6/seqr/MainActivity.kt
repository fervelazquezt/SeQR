package com.equipo6.seqr

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.equipo6.seqr.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

//hello
class MainActivity : AppCompatActivity() {

    val employee = User()
    val db = FirebaseFirestore.getInstance()
    val id = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar(findViewById(R.id.toolbarMain))

        configNav()

//        val bundle = intent.extras
//        val id = bundle?.getString("id")

        ivLogout.setOnClickListener { logout() }

        employee.name = intent.getStringExtra("id").toString()

        val fragment = HomeFragment.newInstance(employee.name, employee.email)
        supportFragmentManager.beginTransaction().replace(R.id.fragContent, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }


    private fun logout() {

        val auth = FirebaseAuth.getInstance()

        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun configNav() {
        val nav = bnvMenu as BottomNavigationView
        nav.setOnNavigationItemSelectedListener {menuItem ->
            showSelectedFragment(menuItem)
            return@setOnNavigationItemSelectedListener true
        }
        //NavigationUI.setupWithNavController(bnvMenu, Navigation.findNavController(this, R.id.fragContent))
    }

    fun showSelectedFragment(menuItem: MenuItem) {
        val intent = Intent()
        val variable = intent.getStringExtra(employee.name)
        if(menuItem.itemId == R.id.navHomeFragment) {
            val fragment = HomeFragment.newInstance(employee.name, employee.email)
            supportFragmentManager.beginTransaction().replace(R.id.fragContent, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        } else if(menuItem.itemId == R.id.navHistoryFragment) {
            val fragment = HistoryFragment.newInstance(employee.name, employee.email)
            supportFragmentManager.beginTransaction().replace(R.id.fragContent, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        } else if(menuItem.itemId == R.id.navTourFragment) {
            val fragment = TourFragment.newInstance(employee.name, employee.email)
            supportFragmentManager.beginTransaction().replace(R.id.fragContent, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
    }


}