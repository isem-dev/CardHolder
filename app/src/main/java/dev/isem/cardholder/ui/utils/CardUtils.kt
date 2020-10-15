package dev.isem.cardholder.ui.utils

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import dev.isem.cardholder.ui.CardDetailsFragment
import kotlinx.android.synthetic.main.card_details_fragment.view.*
import java.util.*

const val NONE = 0

const val VISA = 1
const val MASTERCARD = 2

const val VISA_PREFIX = "4"
const val MASTERCARD_PREFIX = "51,52,53,54,55,"

class CardUtils {

    companion object {

        fun getCardType(cardNumber: String): Int {
            return when {
                cardNumber.substring(0, 1) == VISA_PREFIX -> VISA
                MASTERCARD_PREFIX.contains(cardNumber.substring(0, 2) + ",") -> MASTERCARD
                else -> NONE
            }
        }

        fun isValid(cardNumber: String): Boolean {
            if (!TextUtils.isEmpty(cardNumber) && cardNumber.length >= 4)
                if (getCardType(cardNumber) == VISA && cardNumber.length == 16)
                    return true else if (getCardType(cardNumber) == MASTERCARD && cardNumber.length == 16) return true
            return false
        }

        fun isValidDate(cardValidity: String): Boolean {
            if (!TextUtils.isEmpty(cardValidity) && cardValidity.length == 5) {
                val month = cardValidity.substring(0, 2)
                val year = cardValidity.substring(3, 5)
                val monthPart: Int
                val yearPart: Int
                try {
                    monthPart = month.toInt() - 1
                    yearPart = year.toInt()
                    val current = Calendar.getInstance()
                    current[Calendar.DATE] = 1
                    current[Calendar.HOUR] = 12
                    current[Calendar.MINUTE] = 0
                    current[Calendar.SECOND] = 0
                    current[Calendar.MILLISECOND] = 0
                    val validity = Calendar.getInstance()
                    validity[Calendar.DATE] = 1
                    validity[Calendar.HOUR] = 12
                    validity[Calendar.MINUTE] = 0
                    validity[Calendar.SECOND] = 0
                    validity[Calendar.MILLISECOND] = 0
                    if (monthPart > -1 && monthPart < 12 && yearPart > -1) {
                        validity[Calendar.MONTH] = monthPart
                        validity[Calendar.YEAR] = yearPart + 2000
                    } else return false
                    Log.d("Util", "isValidDate: " + current.compareTo(validity))
                    if (current <= validity) return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return false
        }

        fun continueCheck(continueButton: Button) {
            continueButton.isEnabled = (CardExpiryTextWatcher.continueButtonEnablingFlag
                    && CardNumberTextWatcher.continueButtonEnablingFlag
                    && CardDetailsFragment.continueButtonEnablingFlag)
        }
    }
}

fun TextInputEditText.afterTextChangedContinueCheck(continueButton: Button) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            CardUtils.continueCheck(continueButton)
        }

        override fun afterTextChanged(p0: Editable?) {
            if (p0?.length == 3) CardDetailsFragment.continueButtonEnablingFlag = true
            CardUtils.continueCheck(continueButton)
        }
    })
}
