package com.example.basicbankingapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

class TransactionStatusActivity : AppCompatActivity() {
    private var status : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_status)

        var toolbar =
            findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_transactionStatus_activity)
        toolbar.setBackgroundResource(android.R.color.transparent)
//
//        setSupportActionBar(toolbar)
//        val actionbar = supportActionBar
//
//
//        if(actionbar != null){
//            actionbar.setDisplayHomeAsUpEnabled(true)
//            actionbar.title = "Transaction Status"
//        }


        status  = (intent.getStringExtra("status"))!!.toInt()
        SetView(status)

        Handler(Looper.getMainLooper()).postDelayed({
            val Intent = Intent(this,MainScreenActivity::class.java)
            startActivity(Intent)
        }, 5000)
    }

        private fun SetView(status: Int) {
            if (status == 1) {

                try {
                    val player = MediaPlayer.create(applicationContext, R.raw.pay_success)
                    player!!.isLooping = false
                    player!!.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                findViewById<LottieAnimationView>(R.id.transactionStatusImage).setAnimation(R.raw.payment_success)
                findViewById<TextView>(R.id.tvTransactionStatus).text = "Successfully paid"
                findViewById<TextView>(R.id.tvReceiverNameTransaction).text =
                    "${intent.getStringExtra("ReceiverName")}"
                findViewById<TextView>(R.id.tvTransactionId).text =
                    "Transaction Id :  ${intent.getStringExtra("TransactionId")}"
                findViewById<TextView>(R.id.tvgreeting).text = "Thank You!!\nFor Using Bank Square"
                findViewById<ImageView>(R.id.ivJust).setImageDrawable(resources.getDrawable(R.drawable.party_popper))
            } else {
                findViewById<LottieAnimationView>(R.id.transactionStatusImage).setAnimation(R.raw.payment_failed_error)
                findViewById<TextView>(R.id.tvTransactionStatus).text = "Transaction Failed"
                findViewById<TextView>(R.id.tvReceiverNameTransaction).text =
                    "${intent.getStringExtra("ReceiverName")}"
                findViewById<TextView>(R.id.tvTransactionId).text =
                    "Transaction Id : ${intent.getStringExtra("TransactionId")}"
                findViewById<TextView>(R.id.tvgreeting).text =
                    "Sorry could not process transaction.\nTry after some time."
                findViewById<ImageView>(R.id.ivJust).setImageDrawable(resources.getDrawable(R.drawable.delay))
            }
        }

        override fun onBackPressed() {

        }


}
