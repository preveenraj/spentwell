package com.spentwell.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spentwell.data.models.ExpenseType
import com.spentwell.utils.isNotNullOrEmpty

class ExpenseEntryViewModel : ViewModel() {

    val expenseName = MutableLiveData<String>()
    val expenseAmount = MutableLiveData<String>()

    private val _expenseType = MutableLiveData<ExpenseType>()
    val expenseType: LiveData<ExpenseType>
        get() = _expenseType

    private val _isSaveButtonEnabled = MutableLiveData<Boolean>()
    val isSaveButtonEnabled: LiveData<Boolean>
        get() = _isSaveButtonEnabled

    private val expenseMediator = MediatorLiveData<String>().apply {
        addSource(expenseName) { value ->
            setValue(value)
            sendButtonValidation()
        }
        addSource(expenseAmount) { value ->
            setValue(value)
            sendButtonValidation()
        }
    }.also {
        it.observeForever {
            /* empty */
        }
    }

    private fun sendButtonValidation() {
        _isSaveButtonEnabled.value =
            expenseAmount.value.isNotNullOrEmpty() && expenseName.value.isNotNullOrEmpty()
    }

    fun setExpenseType(expenseType: ExpenseType) {
        _expenseType.value = expenseType
    }

    init {
        expenseName.value = ""
        _expenseType.value = ExpenseType.NECESSITY
        expenseAmount.value = ""
    }

}