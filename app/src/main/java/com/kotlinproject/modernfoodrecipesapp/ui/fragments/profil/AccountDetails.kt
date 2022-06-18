package com.kotlinproject.modernfoodrecipesapp.ui.fragments.profil


import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Window
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.kotlinproject.modernfoodrecipesapp.R
import com.kotlinproject.modernfoodrecipesapp.utils.loadingDialog
import kotlinx.android.synthetic.main.activity_account_details.*
import com.kotlinproject.modernfoodrecipesapp.ui.MainActivity

import android.app.ProgressDialog
import android.widget.TextView


class AccountDetails : AppCompatActivity() {

    lateinit var loadingDialog : loadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getActionBar()?.setTitle("");
        getSupportActionBar()?.setTitle("");
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)
        loadingDialog = loadingDialog(this)
        getUserInfo()

    }

    fun getUserInfo(){
        loadingDialog.startLoadingDialog()
        accountDetailNameEditText.setInputType(InputType.TYPE_NULL);
        accountDetailSurnameEditText.setInputType(InputType.TYPE_NULL);
        accountDetailEmailEditText.setInputType(InputType.TYPE_NULL);
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance()
        val currentUser = user.currentUser!!.uid
        val docRef = db.collection("users").document(currentUser)
        docRef.get()
            .addOnSuccessListener { users ->
                if (users != null) {
                    loadingDialog.dismisDialog()
                    accountDetailNameEditText.setText(users.getString("firstname"), TextView.BufferType.SPANNABLE)
                    accountDetailSurnameEditText.setText(users.getString("secondname"), TextView.BufferType.SPANNABLE)
                    accountDetailEmailEditText.setText(users.getString("email"), TextView.BufferType.SPANNABLE)
                } else {
                    loadingDialog.dismisDialog()
                  }
            }
            .addOnFailureListener { exception ->
                loadingDialog.dismisDialog()
                }
    }
}