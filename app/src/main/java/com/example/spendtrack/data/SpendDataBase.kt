package com.example.spendtrack.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SpendModel::class], version = 1, exportSchema = false)
@TypeConverters(SpendConverter::class)

abstract class SpendDataBase : RoomDatabase() {
    abstract fun getSpendDao(): SpendDao
}

//here we have settled up the database class with name spend database
//which is an  abstract class containing a function named as
//getSpendDao which returns SpendDao which ultimately gives access
//to crud functions
