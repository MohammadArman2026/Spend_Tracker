package com.example.spendtrack.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao

abstract class SpendDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertSpend(spendModel: SpendModel)

    @Query("DELETE FROM `spend_table` WHERE id = :id")
    abstract suspend fun deleteSpend(id: Long)

    @Query("SELECT * FROM spend_table")
    abstract fun getAllSpend(): Flow<List<SpendModel>>

    @Query("SELECT SUM(cost) FROM spend_table")
    abstract fun calTotalCost(): Flow<Long?>
    @Query("DELETE FROM spend_table")
    abstract suspend fun deleteAll()
}


/*here we have settled up Dao class which is an abstract class
containing functions of crud operation most of them which can take
long time  to execute are marked suspend because they can cause your
app to crash so to avoid that we use coroutines
when we will call those function at that place we will be using
coroutines to execute them*/