package dev.isem.cardholder.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _cardNumber = MutableLiveData<String>()
    val cardNumber: LiveData<String>
        get() = _cardNumber

    private val _cardExpiryDate = MutableLiveData<String>()
    val cardExpiryDate: LiveData<String>
        get() = _cardExpiryDate

    private val _cardCvvCode = MutableLiveData<String>()
    val cardCvvCode: LiveData<String>
        get() = _cardCvvCode

    private val _amountAdd = MutableLiveData<String>()
    val amountAdd: LiveData<String>
        get() = _amountAdd

    fun setCardNumber(cardNum: String) {
        _cardNumber.value = cardNum
    }

    fun setCardExpiryDate(cardExpDate: String) {
        _cardExpiryDate.value = cardExpDate
    }

    fun setCardCvvCode(cardCvv: String) {
        _cardCvvCode.value = cardCvv
    }

    fun setAmountAdd(amount: String) {
        _amountAdd.value = amount
        Log.d("MainViewModel", amountAdd.value.toString())
    }

}