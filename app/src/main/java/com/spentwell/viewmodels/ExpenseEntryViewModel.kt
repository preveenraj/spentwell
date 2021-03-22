package com.spentwell.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.spentwell.data.models.Expense
import com.spentwell.data.models.ExpenseType
import com.spentwell.data.repository.ExpenseRepository
import com.spentwell.utils.isNotNullOrEmpty
import kotlinx.coroutines.launch
import java.lang.Double
import java.util.*

class ExpenseEntryViewModel(application: Application) : AndroidViewModel(application) {

    var expense: Expense? = null

    val expenseName = MutableLiveData<String>()
    val expenseAmount = MutableLiveData<String>()

    private val _expenseType = MutableLiveData<ExpenseType>()
    val expenseType: LiveData<ExpenseType>
        get() = _expenseType

    private val _eventSubmitExpense = MutableLiveData<Boolean>()
    val eventSubmitExpense: LiveData<Boolean>
        get() = _eventSubmitExpense

    private val _isSaveButtonEnabled = MutableLiveData<Boolean>()
    val isSaveButtonEnabled: LiveData<Boolean>
        get() = _isSaveButtonEnabled

    private val _eventCloseNavigation = MutableLiveData<Boolean>()
    val eventCloseNavigation: LiveData<Boolean>
        get() = _eventCloseNavigation

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

    fun onSubmitExpense() {
        _isSaveButtonEnabled.value = false
        println("submit clicked")
        createExpense()
        _eventSubmitExpense.value = true
    }

    private fun createExpense() {
        expense = Expense(
            name = expenseName.value!!,
            type = expenseType.value!!,
            amount = Double.parseDouble(expenseAmount.value!!),
            dateTime = Date()
        )
        viewModelScope.launch {
            ExpenseRepository(application = getApplication()).addNewExpense(expense!!)
        }
        onSubmissionCompleted()

    }

    fun onSubmissionCompleted() {
        expenseName.value = ""
        expenseAmount.value = ""
        _expenseType.value = ExpenseType.NECESSITY
        expense = null
        sendButtonValidation()
        _eventSubmitExpense.value = false
        _eventCloseNavigation.value = true
    }

    fun onCloseNavigationCompleted() {
        _eventCloseNavigation.value = false
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