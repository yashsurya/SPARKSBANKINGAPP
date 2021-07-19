package com.example.basicbankingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess


class MainScreenActivity : AppCompatActivity() {
    private var BackPressTwice : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        val ibLogin = findViewById<ImageButton>(R.id.iblogin)

        ibLogin.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }


        FitCursor.fix()


        val db = Database(this)
        var count =db.getCount()
            if(db.getCount() < 1){
            var customer = ArrayList<Customer>()

            val customer1 = Customer(
                1,
                "Chris Evans",
                "chrisevans@bs.com",
                "562-545-7891",
                DBbitmapUtility.getBytes(DBbitmapUtility.drawableToBitmap(resources.getDrawable(com.example.basicbankingapp.R.drawable.chris_evans))),
                "1250900"
            )
            val customer2 = Customer(
                2,
                "Chris Hemsworth",
                "chrishems@bs.com",
                "667-526-9878",
                DBbitmapUtility.getBytes(DBbitmapUtility.drawableToBitmap(resources.getDrawable(com.example.basicbankingapp.R.drawable.chris_hemsworth))),
                "1320090"
            )


            val customer3 = Customer(
                3,
                "Chris Pratt",
                "chrisppratt@bs.com",
                "598-512-1567",
                DBbitmapUtility.getBytes(DBbitmapUtility.drawableToBitmap(resources.getDrawable(com.example.basicbankingapp.R.drawable.chris_pratt))),
                "1359000"
            )

            val customer4 = Customer(
                4,
                "Elizabeth Olsen",
                "elizabeth@bs.com",
                "785-667-5236",
                DBbitmapUtility.getBytes(DBbitmapUtility.drawableToBitmap(resources.getDrawable(com.example.basicbankingapp.R.drawable.elizabeth_olsen))),
                "12500000"
            )

            val customer5 = Customer(
                5,
                "Mark Ruffalo",
                "mark_ruffalo@bs.com",
                "632-257-1859",
                DBbitmapUtility.getBytes(DBbitmapUtility.drawableToBitmap(resources.getDrawable(com.example.basicbankingapp.R.drawable.mark_ruffalo))),
                "1850360"
            )

            val customer6 = Customer(
                6,
                "Robert Downey Jr",
                "robert_jr@bs.com",
                "485-251-3698",
                DBbitmapUtility.getBytes(DBbitmapUtility.drawableToBitmap(resources.getDrawable(com.example.basicbankingapp.R.drawable.robert_downey_jr))),
                "1985200"
            )

            val customer7 = Customer(
                7,
                "Scarlet Johanson",
                "Scarlet@bs.com",
                "897-456-1285",
                DBbitmapUtility.getBytes(DBbitmapUtility.drawableToBitmap(resources.getDrawable(com.example.basicbankingapp.R.drawable.scarlet_johanson))),
                "2450030"
            )

            val customer8 = Customer(
                8,
                "Sebastin Stan",
                "sebastin_stan@bs.com",
                "562-545-782",
                DBbitmapUtility.getBytes(DBbitmapUtility.drawableToBitmap(resources.getDrawable(com.example.basicbankingapp.R.drawable.sebastin_stan))),
                "1100500"
            )

            val customer9 = Customer(
                9,
                "Vijay",
                "vijay@bs.com",
                "856-152-8752",
                DBbitmapUtility.getBytes(DBbitmapUtility.drawableToBitmap(resources.getDrawable(com.example.basicbankingapp.R.drawable.vijay))),
                "1200500"
            )

            val customer10 = Customer(
                10,
                "Surya",
                "surya@bs.com",
                "856-152-9874",
                DBbitmapUtility.getBytes(DBbitmapUtility.drawableToBitmap(resources.getDrawable(com.example.basicbankingapp.R.drawable.surya))),
                "1200500"
            )

            customer.add(customer1)
            customer.add(customer2)
            customer.add(customer3)
            customer.add(customer4)
            customer.add(customer5)
            customer.add(customer6)
            customer.add(customer7)
            customer.add(customer8)
            customer.add(customer9)
            customer.add(customer10)


            var db : Database = Database(this)
            var status = db.addCustomer(customer)

            if(status > -1){
                Toast.makeText(this, "Success adding data", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Failure adding data", Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onBackPressed() {
        if(BackPressTwice == true){
            finish()
            exitProcess(0)
        }
        Toast.makeText(this, "Press once again to exit", Toast.LENGTH_SHORT).show()
        BackPressTwice = true
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            BackPressTwice = false
        }, 2000)
    }

}