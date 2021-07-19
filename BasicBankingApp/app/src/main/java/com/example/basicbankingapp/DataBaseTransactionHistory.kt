package com.example.basicbankingapp
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseTransactionHistory(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION){


    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "EmployeeDatabase"
        private const val TABLE_CONTACTS = "TransactionTable"
        private const val COLUMN_ID = "transac_id"
        private const val COLUMN_SENDER_NAME = "sender_name"
        private const val COLUMN_RECEIVER_NAME = "receiver_name"
        private const val COLUMN_AMOUNT = "amount"
        private const val COLUMN_SENDER_ID = "sender_id"
        private const val COLUMN_RECEIVER_ID = "receiver_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val Create_table = ("CREATE TABLE ${TABLE_CONTACTS} (" +
                "${COLUMN_ID} TEXT," +
                "${COLUMN_SENDER_ID} TEXT," +
                "${COLUMN_SENDER_NAME} TEXT," +
                "${COLUMN_RECEIVER_ID} TEXT," +
                "${COLUMN_RECEIVER_NAME} TEXT," +
                "${COLUMN_AMOUNT} TEXT )")
        db?.execSQL(Create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS ${TABLE_CONTACTS}")
        onCreate(db)
    }

    fun addTransaction(transactionId : String?,sender_name : String?,sender_id : String?,receiver_id : String,receiver_name: String?,amount : String?) : Long{
        val db = this.writableDatabase
        var status : Long = -1
        val content = ContentValues()
        content.put(COLUMN_ID, transactionId)
        content.put(COLUMN_SENDER_NAME, sender_name)
        content.put(COLUMN_SENDER_ID, sender_id)
        content.put(COLUMN_RECEIVER_ID, receiver_id)
        content.put(COLUMN_RECEIVER_NAME, receiver_name)
        content.put(COLUMN_AMOUNT, amount)
        status = db.insert(TABLE_CONTACTS, null, content)
        return status
    }
}