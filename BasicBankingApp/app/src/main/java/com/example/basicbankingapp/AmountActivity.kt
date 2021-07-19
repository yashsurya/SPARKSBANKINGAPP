package com.example.basicbankingapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.basicbankingapp.R
import com.google.android.material.textfield.TextInputLayout

class AmountActivity : AppCompatActivity() {
    private var amount: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount)

        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_amount_activity)

        setSupportActionBar(toolbar)

        val actionbar = supportActionBar

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "Amount"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        findViewById<LinearLayout>(R.id.llPreviewButton).setOnClickListener {
            amount = findViewById<EditText>(R.id.etamount).text.toString()
            val status = testBalance(amount)
            if(status) {
                val Intent = Intent(this, ConfirmationActivity::class.java)
                Intent.putExtra("name", "${intent.getStringExtra("name")}")
                Intent.putExtra("name1","${intent.getStringExtra("name1")}")
                Intent.putExtra("amount", "${amount}")
                startActivity(Intent)
            }
            else{
                customDialogForInsufficientBalance()
            }
        }
    }

    private fun testBalance(amount: String?) : Boolean{
        val db = Database(this)
        Log.i("402sri",intent.getStringExtra("name")!!)
        var customer : Customer? = db.getUserDetails(intent.getStringExtra("name")!!)
        Log.i("1001Check","${customer!!.name}")
        return customer!!.balance.toDouble() >= amount!!.toDouble()
    }

    private fun customDialogForInsufficientBalance(){
        val customDialog = Dialog(this)
        customDialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
        customDialog.setContentView(R.layout.custom_dialog_insufficient_balance)
        customDialog.findViewById<Button>(R.id.tvOk).setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }
}
