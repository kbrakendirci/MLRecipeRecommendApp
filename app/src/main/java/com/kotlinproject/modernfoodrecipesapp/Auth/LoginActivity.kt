package com.kotlinproject.modernfoodrecipesapp.Auth
import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kotlinproject.modernfoodrecipesapp.R
import com.kotlinproject.modernfoodrecipesapp.ui.MainActivity
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth
         super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setup()
    }



    fun LoginActivity.buttonActions(auth : FirebaseAuth){
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
        btnRegLogin.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }
        LoginFromForgotPassword.setOnClickListener {
            startActivity(Intent(this,ForgotMyPassword::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)

        }

    }
    private fun setup(){

        etLoginEmail.addTextChangedListener(textWatcher)
        etLoginPassword.addTextChangedListener(textWatcher)

        LoginBtn.isClickable = false
        LoginBtn.isEnabled = false


        // Initialize Firebase Auth
        auth = Firebase.auth

        buttonActions(auth)
        closeKeyboard(loginMainLayout)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override
        fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if(isEmailValid(etLoginEmail.text.toString()) && etLoginPassword.text!!.isNotEmpty()){
                //TODO: button active disactive
                LoginBtn.isClickable = true
                LoginBtn.isEnabled = true

            }else  {
                LoginBtn.isClickable = false
                LoginBtn.isEnabled = false
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    }
}

///give another layout id for view
@SuppressLint("ClickableViewAccessibility")
fun AppCompatActivity.closeKeyboard(view : View){
    //hide keyboard when touching the screen
    view.setOnTouchListener { v, event ->
        val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        true
    }
}

fun AppCompatActivity.isEmailValid(email: String): Boolean {
    var isValid = false
    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val inputStr: CharSequence = email
    val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(inputStr)
    if (matcher.matches()) {
        isValid = true
    }
    return isValid
}