package com.kotlinproject.modernfoodrecipesapp.Auth

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kotlinproject.modernfoodrecipesapp.R
import com.kotlinproject.modernfoodrecipesapp.ui.MainActivity
import com.kotlinproject.modernfoodrecipesapp.utils.loadingDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var loadingDialog : loadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setup()
    }
    override fun onBackPressed() {
        startActivity(Intent(this,LoginActivity::class.java))
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }
    private fun setup(){
        closeKeyboard(registerPageMainLayout)
        auth = Firebase.auth
        loadingDialog = loadingDialog(this)
        button(auth)
         }

    private fun button(auth : FirebaseAuth) {
        RegisterBtn.setOnClickListener {
            loadingDialog.startLoadingDialog()
            if (etRegisterName.text!!.isNotEmpty() && etRegisterEmail.text!!.isNotEmpty() && etRegisterPassword.text!!.isNotEmpty()&& etRegisterPasswordAgain.text!!.isNotEmpty())
            { if (etRegisterPassword.text.toString().equals(etRegisterPasswordAgain.text.toString()) ) {
                newMemberRegister(
                    etRegisterEmail.text.toString(),
                    etRegisterPassword.text.toString())
            }
            else {
                loadingDialog.dismisDialog()
                showOneActionAlert("Error!","Your passwords are not same, please try again.",null)
            }
            } else {
                loadingDialog.dismisDialog()
                showOneActionAlert("Error!","Something went wrong, please try again.",null)
            }
        }

        LoginfromRegisterBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
        }
    }
    //New member register function
    private fun newMemberRegister(mail: String, password: String) {
        auth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun saveFireStore(firstName: String, userEmail: String, userUid : String) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["firstname"] = firstName
        user["email"] = userEmail
        user["count"]= 0
        user["favorites"]= listOf("İstanbul", "Tekirdağ", "Ankara", "İzmir")
        db.collection("users").document(userUid)
            .set(user)
            .addOnSuccessListener {
                sendConfirmationEmail()
                Toast.makeText(this, "You Signed In successfully", Toast.LENGTH_LONG).show()

            }
            .addOnFailureListener {
                auth.currentUser?.delete()
            }
    }
    fun updateUI(account: FirebaseUser?) {
        if (account != null) {
            saveFireStore(etRegisterName.text.toString(),etRegisterEmail.text.toString(), account.uid)
            onBackPressed()
        } else {
            loadingDialog.dismisDialog()
            showOneActionAlert("Error!","Something went wrong, please try again.",null)
        }
    }
    // Send to new member check email
    private fun sendConfirmationEmail(){
        val User = auth.currentUser;
        User!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loadingDialog.dismisDialog()
                    showOneActionAlert("Success","Verification email sent to "+ User.getEmail()) {
                        FirebaseAuth.getInstance().signOut()
                        finish()
                    }
                    } else {
                    Log.e(ContentValues.TAG, "sendEmailVerification", task.exception)
                    loadingDialog.dismisDialog()
                    showOneActionAlert("Error","" + task.exception?.message,null)
                }
            }
    }

}
fun Activity.showOneActionAlert(title: String, detailText: String, buttonFunction: (() -> Unit)?){
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(detailText)
    builder.setPositiveButton("Okay"){ dialogInterface, i ->
        if (buttonFunction != null) {
            buttonFunction()
        }
    }
    builder.show()
}