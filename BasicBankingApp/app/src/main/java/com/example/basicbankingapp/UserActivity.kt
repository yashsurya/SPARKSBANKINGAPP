package com.example.basicbankingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class UserActivity : AppCompatActivity() {
    private var UserDataList : ArrayList<Customer>? = null
    private var customerAdapter : CustomerAdaptor? = null
    public var name :String? = null
    var entry : String? = "sender"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_Useractivity)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar


        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "Choose User"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        name  = intent.getStringExtra("name")
        if(name == null){
            val db : Database = Database(this)
            FitCursor.fix()
            UserDataList = db.getCustomersData()
            entry = "sender"
            setupUserListRecyclerView()
        }
        else if(name != null){
            actionbar!!.title = "Choose Recipient"
            val db : Database = Database(this)
            FitCursor.fix()
            UserDataList = db.getCustomersData(name!!)
            entry = "receiver".toString()
            setupUserListRecyclerView()
        }




    }

    private fun setupUserListRecyclerView() {
        var rvUserList = findViewById<RecyclerView>(R.id.rvUsersList)

        rvUserList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        customerAdapter = CustomerAdaptor(this, UserDataList!!,name)

        rvUserList.adapter = customerAdapter

    }
}