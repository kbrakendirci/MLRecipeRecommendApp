package com.kotlinproject.modernfoodrecipesapp.ui.fragments.profil


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kotlinproject.modernfoodrecipesapp.Auth.showOneActionAlert
import com.kotlinproject.modernfoodrecipesapp.R
import com.kotlinproject.modernfoodrecipesapp.utils.loadingDialog
import kotlinx.android.synthetic.main.activity_change_my_password.*


class ChangeMyPassword : AppCompatActivity() {
    lateinit var loadingDialog : loadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getActionBar()?.setTitle("");
        getSupportActionBar()?.setTitle("");
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_my_password)
        loadingDialog = loadingDialog(this)
        setup()
        changePasswordButton.setOnClickListener{
            //(TODO) : check password fields
            updatePassword(newPasswordTextInputEditText.text.toString())
        }
    }
    private fun setup(){
        oldPasswordTextInputEditText.addTextChangedListener(textWatcher)
        newPasswordTextInputEditText.addTextChangedListener(textWatcher)
        newPasswordAgainTextInputEditText.addTextChangedListener(textWatcher)
        changePasswordButton.isClickable=false
        changePasswordButton.isEnabled=false
        changePasswordButton.alpha=0.5F

    }


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if(oldPasswordTextInputEditText.text.toString().isNotEmpty()&&newPasswordTextInputEditText.text.toString().isNotEmpty()
                &&newPasswordAgainTextInputEditText.text.toString().isNotEmpty()){
                changePasswordButton.isClickable=true
                changePasswordButton.isEnabled=true
                changePasswordButton.alpha=1F

            }else  {
                changePasswordButton.isClickable=false
                changePasswordButton.isEnabled=false
                changePasswordButton.alpha=1F
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    }


    private fun updatePassword(newPasswordText : String) {
        loadingDialog.startLoadingDialog()
        if (newPasswordAgainTextInputEditText.text.toString().equals(newPasswordTextInputEditText.text.toString())) {
            val user = Firebase.auth.currentUser
            val newPassword = newPasswordText

            user!!.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loadingDialog.dismisDialog()
                        showOneActionAlert("Success","Updated your password successfully.",){
                            oldPasswordTextInputEditText.text?.clear()
                            newPasswordTextInputEditText.text?.clear()
                            newPasswordAgainTextInputEditText.text?.clear()
                        }
                    } else {
                        loadingDialog.dismisDialog()
                        showOneActionAlert("Error","" + task.exception?.message,null)
                    }
                }
        }else{
            loadingDialog.dismisDialog()
            showOneActionAlert("Error","Passwords didn't match, your new password fields must be the same. " ,null)
        }
    }

}


