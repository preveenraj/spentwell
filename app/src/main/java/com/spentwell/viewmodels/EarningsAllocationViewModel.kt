package com.spentwell.viewmodels

import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spentwell.data.models.ExpenseType

class EarningsAllocationViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val _isProceedButtonEnabled = MutableLiveData<Boolean>()
    val isProceedButtonEnabled: LiveData<Boolean>
        get() = _isProceedButtonEnabled

//    val necessitiesValue = MutableLiveData<Int>()
//    val luxuriesValue = MutableLiveData<Int>()
//    val savingsValue = MutableLiveData<Int>()

//    private val expenseMediator = MediatorLiveData<Int>().apply {
//        addSource(necessitiesValue) { value ->
//            val me = ((value/5).toInt() * 5)
//            Log.i("asdas", me.toString())
//            setValue(me)
//        }
//        addSource(luxuriesValue) { value ->
//            setValue((value/5).toInt() * 5)
//        }
//        addSource(savingsValue) { value ->
//            setValue((value/5).toInt() * 5)
//        }
//    }.also {
//        it.observeForever {
//            /* empty */
//        }
//    }



    init {
       _isProceedButtonEnabled.value = true
    }

    fun toggleProceedButton(b: Boolean) {
        _isProceedButtonEnabled.value = b
    }
}