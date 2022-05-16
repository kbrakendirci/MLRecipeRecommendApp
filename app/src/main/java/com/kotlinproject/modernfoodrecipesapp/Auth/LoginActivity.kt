package com.kotlinproject.modernfoodrecipesapp.Auth
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kotlinproject.modernfoodrecipesapp.R
import com.kotlinproject.modernfoodrecipesapp.ui.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnRegLogin.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }
        LoginBtn.setOnClickListener{
            auth.signInWithEmailAndPassword(etLoginEmail.text.toString(), etLoginPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        val user = auth.currentUser
                        if(user!!.isEmailVerified){
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this,"User isnt verifide",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(baseContext, "Authentication failed." + task.exception!!.localizedMessage,
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        fun sendEmailControl(user: FirebaseUser?){
            if(user!!.isEmailVerified){
                Toast.makeText(this,"user verified", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"User isnt verifide", Toast.LENGTH_SHORT).show()
            }
        }
    }


}