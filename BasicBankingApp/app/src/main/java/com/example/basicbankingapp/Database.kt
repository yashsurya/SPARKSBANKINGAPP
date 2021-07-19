package com.example.basicbankingapp

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "EmployeeDatabase"
        private const val TABLE_CONTACTS = "EmployeeTable"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_MOBILE_NO = "mobile"
        private const val COLUMN_PROFILE_PHOTO = "profile_photo"
        private const val COLUMN_BALANCE = "balance"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val Create_table = ("CREATE TABLE $TABLE_CONTACTS (" +
                "$COLUMN_ID INTEGER," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_MOBILE_NO TEXT," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_BALANCE TEXT," +
                "$COLUMN_PROFILE_PHOTO BLOB )")
        db?.execSQL(Create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addCustomer(customer: ArrayList<Customer>) : Long{
        val db = this.writableDatabase
        var status : Long = -1

        for (i in 0..customer.size-1) {
            val content = ContentValues()
            content.put(COLUMN_ID, customer[i].id)
            content.put(COLUMN_NAME, customer[i].name)
            content.put(COLUMN_MOBILE_NO, customer[i].mobile_no)
            content.put(COLUMN_EMAIL, customer[i].email)
            content.put(COLUMN_BALANCE, customer[i].balance)
            content.put(COLUMN_PROFILE_PHOTO, customer[i].profile_photo)
            status = db.insert(TABLE_CONTACTS, null, content)
            if(status <= -1){
                break
            }
        }
        db.close()
        return status
    }

    fun getCount() : Long{
        val db = this.readableDatabase
        var count = DatabaseUtils.queryNumEntries(db, TABLE_CONTACTS)
        db.close()
        return count
    }

    fun getUserDetails(name : String = "") : Customer{
            var customersList  = ArrayList<Customer>()
            var id : Int
            var username : String
            var profileImage : ByteArray
            var balance : String
            var email : String
            var mobile : String

            val new_id = "\"$name\""

            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM $TABLE_CONTACTS WHERE $COLUMN_NAME = $new_id", null)
             if(cursor == null){
                Log.e("401Sri","Cursor null")
            }
            if( cursor != null ){
                Log.e("401Sri","Cursor is not null")
                cursor.moveToFirst()
            }

            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            username = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            profileImage = cursor.getBlob(cursor.getColumnIndex(COLUMN_PROFILE_PHOTO))
            balance = cursor.getString(cursor.getColumnIndex(COLUMN_BALANCE))
            email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
            mobile = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NO))

                val tempObj = Customer(id, username, email, mobile, profileImage, balance)

            return tempObj
    }

    fun updateBalanceData(Balance : String?,name: String?): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_BALANCE,Balance)
        val new_name = "\"$name\""
        val status = db.update(TABLE_CONTACTS, contentValues , "$COLUMN_NAME = $new_name",null)

        return status
    }


    fun getCustomersData(name: String = "") : ArrayList<Customer>{
        var customersList  = ArrayList<Customer>()
        var id : Int
        var username : String
        var profileImage : ByteArray
        var balance : String
        var email : String
        var mobile : String

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CONTACTS", null)
        var Count : Int = 0
        while (cursor.moveToNext() && Count < 9){
            id = cursor.getColumnIndex(COLUMN_ID)
            username = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            profileImage = cursor.getBlob(cursor.getColumnIndex(COLUMN_PROFILE_PHOTO))
            balance = cursor.getString(cursor.getColumnIndex(COLUMN_BALANCE))
            email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
            mobile = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NO))

            val tempObj = Customer(id, username, email, mobile, profileImage, balance)
            if(name != tempObj.name.toString()) {
                customersList.add(tempObj)
            }
            Count++
        }
        return customersList
    }



}