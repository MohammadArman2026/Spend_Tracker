package com.example.spendtrack.data

import kotlinx.coroutines.flow.Flow


class SpendRepository(private val spendDao: SpendDao) {
    fun getAllSpend(): Flow<List<SpendModel>> = spendDao.getAllSpend()

    suspend fun insertSpend(spendModel: SpendModel) = spendDao.insertSpend(spendModel)

    suspend fun deleteSpend(id: Long) = spendDao.deleteSpend(id)

    fun calTotalCost(): Flow<Long?> = spendDao.calTotalCost() // ✅ no suspend

    suspend fun deleteAll() = spendDao.deleteAll()//to delete all the data from table
}

//here we have settled up the repository class which is a class
//having dependency injection in such a way that it would be able to use
//Dao object and call all its method with the help of that dependency injection