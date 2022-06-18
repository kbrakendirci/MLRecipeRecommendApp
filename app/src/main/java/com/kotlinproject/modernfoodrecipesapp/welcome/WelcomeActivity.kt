package com.kotlinproject.modernfoodrecipesapp.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import com.kotlinproject.modernfoodrecipesapp.R
import com.kotlinproject.modernfoodrecipesapp.ui.fragments.onboard.onBoardActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        var yukaridanAsagi = AnimationUtils.loadAnimation(this,R.anim.yukaridan_asagi)
        var asagidanyulari = AnimationUtils.loadAnimation(this,R.anim.asagidan_yukari)
        var bulunduguyerdenAsagi = AnimationUtils.loadAnimation(this,R.anim.bulunanyerden_asagi)

        imageView.animation=yukaridanAsagi
        imageView3.animation=asagidanyulari
        button.setOnClickListener {

            imageView3.startAnimation(bulunduguyerdenAsagi)


            //Animasyon bitmeden 1sn sonra MainActivity açılsın
            object : CountDownTimer(1000,1000){
                override fun onFinish() {
                    var intent = Intent(applicationContext, onBoardActivity::class.java)
                    startActivity(intent)
                }

                override fun onTick(p0: Long) {
                    //işimiz yok burasıyla
                }

            }.start()



        }

    }
}