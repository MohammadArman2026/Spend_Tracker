package com.example.spendtrack.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SpendViewModel(private val spendRepository: SpendRepository = SpendGraph.spendRepository) :
    ViewModel() {
    val totalCost: Flow<Long?> = spendRepository.calTotalCost()

    lateinit var getAllSpend: Flow<List<SpendModel>>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllSpend = spendRepository.getAllSpend()
        }
    }

    fun addToList(spendModel: SpendModel) {
        viewModelScope.launch(Dispatchers.IO) {
            spendRepository.insertSpend(spendModel)
        }
    }

    fun deleteFromList(spendModel: SpendModel) {
        viewModelScope.launch(Dispatchers.IO) {
            spendRepository.deleteSpend(spendModel.id)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            spendRepository.deleteAll()
        }
    }
}


/*this is a view model class which is a subclass of view model
* it takes dependency injection of repository class so that it will be able to access
* all the functions of repository class and so on
* here we are using lateinit var which save us from null pointer exception and it simply
* means that a var initialised with lateinit keyword will not be used until and unless it
* is initialised*/
