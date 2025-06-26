package com.example.spendtrack.data
//this is an entity class for room database
//entity means basically table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "spend_table")
data class SpendModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0L,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "cost")
    var cost: Long,
    @ColumnInfo(name = "date")
    var date: Date
)

//now the table is ready with column id ,which is primary key means unique
// and will be generated automatically,and description ,cost