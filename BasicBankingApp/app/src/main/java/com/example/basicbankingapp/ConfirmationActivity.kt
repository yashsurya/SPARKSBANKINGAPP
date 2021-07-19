package com.example.basicbankingapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.random.Random

class ConfirmationActivity : AppCompatActivity() {
    private var random : String = ""
    private var sender : Customer? = null
    private var receiver : Customer? = null
    private var transactionId : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_confirmation_activity)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar


        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "Confirm Transaction"
        }

        toolbar.setNavigationOnClickListener {
            CustomDialogCancelTransaction()
        }

        val transacId = UUID.randomUUID().toString().substring(0,8)

        transactionId = transacId.toString()
        val sender_name = intent.getStringExtra("name")
        val receiver_name = intent.getStringExtra("name1")
        val amount = intent.getStringExtra("amount")

        val db = Database(this)
        sender = db.getUserDetails(sender_name!!)
        receiver = db.getUserDetails(receiver_name!!)

        findViewById<TextView>(R.id.tvSender).text = sender_name
        findViewById<TextView>(R.id.tvSendingAmount).text = "Paying : $$amount"
        findViewById<TextView>(R.id.tvreceiver).text = receiver_name
        findViewById<TextView>(R.id.tvReceivingAmount).text = "Receiving : $$amount"
        findViewById<ImageView>(R.id.ivSenderImage).setImageBitmap(DBbitmapUtility.getImage(sender!!.profile_photo))
        findViewById<ImageView>(R.id.ivReceiverImage).setImageBitmap(
            DBbitmapUtility.getImage(
                receiver!!.profile_photo
            )
        )
        findViewById<TextView>(R.id.tvTransactionId).text = "Transaction Id : ${transacId.toString()}"



        findViewById<TextView>(R.id.tvCancel).setOnClickListener {
            CustomDialogCancelTransaction()
        }

        findViewById<TextView>(R.id.tvConfirm).setOnClickListener {
            TrasactionUpdate()
        }

    }

    private fun TrasactionUpdate() {
        val sender_name = intent.getStringExtra("name")
        val receiver_name = intent.getStringExtra("name1")
        val db = Database(this)
        if(sender_name != null && receiver_name != null){
            val sender = db.getUserDetails(sender_name!!)
            val receiver = db.getUserDetails(receiver_name!!)
            val amount  = (intent.getStringExtra("amount"))!!.toDouble()
            val sender_balance = ((sender.balance).toDouble() - amount).toString()
            val receiver_balance = ((receiver.balance).toDouble() + amount).toString()
            val sender_status = db.updateBalanceData(sender_balance, sender_name)
            if(sender_status > -1) {
                val receiver_status = db.updateBalanceData(receiver_balance, receiver_name)
                if(receiver_status > -1){
                    val Intent = Intent(this, TransactionStatusActivity::class.java)
                    Intent.putExtra("ReceiverName", "$receiver_name")
                    Intent.putExtra("status", "1")
                    Log.i("transactionID","$transactionId")
                    Intent.putExtra("TransactionId", "$transactionId")
                    Log.i("401Sri", "Successfully added")
                    finish()
                    startActivity(Intent)
                }
                else {
                    val sender_status = db.updateBalanceData(
                        (sender_balance.toDouble() + amount).toString(),
                        sender_name
                    )
                    failureTransaction()
                }
            }
            else{
                failureTransaction()
            }
        }
    }

    private fun failureTransaction(){
        val Intent = Intent(this, TransactionStatusActivity::class.java)
        Intent.putExtra("ReceiverName", "${receiver!!.name}")
        Intent.putExtra("status", "0")
        Intent.putExtra("TransactionId", "$transactionId")
        Log.i("401Sri", "Failed")
        startActivity(Intent)
    }

    private fun CustomDialogCancelTransaction() {
        val customDialog = Dialog(this)
        customDialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
        customDialog.setContentView(R.layout.custom_dialog_cancel_transcation)
        customDialog.findViewById<Button>(R.id.tvYes).setOnClickListener{
            finish()
            customDialog.dismiss()
        }
        customDialog.findViewById<Button>(R.id.tvNo).setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }

    fun rand(start: Int, end: Int, count: Int): String {
        if (count > 10){
            return (Random(System.nanoTime()).nextInt(end - start + 1) + start).toString()
        }
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }

        return random + rand(0, 9, count + 1)

    }
}