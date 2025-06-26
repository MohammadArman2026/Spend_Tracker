package com.example.spendtrack.data

import android.content.Context
import androidx.room.Room

object SpendGraph{
    lateinit var dataBase: SpendDataBase
    val spendRepository by lazy {
        SpendRepository(dataBase.getSpendDao())
    }

    fun provide(context: Context){
        dataBase = Room.databaseBuilder(
            context,
            SpendDataBase::class.java,
            "spend_database"
        ).build()
    }
}

//here we are setting object graph which is to be used
// to create instance of database