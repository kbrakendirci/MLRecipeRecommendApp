package com.kotlinproject.modernfoodrecipesapp.ui.fragments.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.kotlinproject.modernfoodrecipesapp.Auth.LoginActivity
import com.kotlinproject.modernfoodrecipesapp.R
import kotlinx.android.synthetic.main.fragment_profil.*
import androidx.appcompat.app.AppCompatActivity




class ProfilFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profil, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeMyPasswordLayout.setOnClickListener {
            val action =
                ActionOnlyNavDirections(R.id.action_navigation_authentication_to_change_My_Password)
            Navigation.findNavController(it).navigate(action)
        }
        myAccountLayout.setOnClickListener {
            val action =
                ActionOnlyNavDirections(R.id.action_navigation_authentication_to_my_Account)
            Navigation.findNavController(it).navigate(action)
        }

        signOutLayout.setOnClickListener {
            logOut()
        }
    }
    private fun logOut(){
        FirebaseAuth.getInstance().signOut()
        activity?.let{
            val intent = Intent (it, LoginActivity::class.java)
            it.startActivity(intent)
            it.finish()
        }
    }
}


