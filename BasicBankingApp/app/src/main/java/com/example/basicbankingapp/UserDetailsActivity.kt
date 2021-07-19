package com.example.basicbankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.log


class UserDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_userDetail)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "Details"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        FitCursor.fix()
        setUpUserDetails()

        findViewById<LinearLayout>(R.id.lltransferButton).setOnClickListener{
            val Intent = Intent(this,UserActivity::class.java)
            Intent.putExtra("name","${intent.getStringExtra("name")}")
            Log.i("401sri","${intent.getStringExtra("name")}")
            startActivity(Intent)
        }
    }

    private fun setUpUserDetails(){
        val db = Database(this)
//        Toast.makeText(this,intent.getIntExtra("id",0),Toast.LENGTH_LONG).show()
        //Log.i("201","${intent.getStringExtra("name")!!}")
        var customer : Customer =  db.getUserDetails(intent.getStringExtra("name")!!)

        var profile = findViewById<CircleImageView>(R.id.CirularIvProfilePhoto)
        var name = findViewById<TextView>(R.id.tvUserdetailName)
        var balance = findViewById<TextView>(R.id.tvUserdetailBalance)
        var email = findViewById<TextView>(R.id.tvUserdetailemail)


        profile.setImageBitmap(DBbitmapUtility.getImage(customer.profile_photo))
        name.text = customer.name.toString()
        balance.text = "Balance : " + customer.balance.toString()
        email.text = customer.email.toString()
    }
}