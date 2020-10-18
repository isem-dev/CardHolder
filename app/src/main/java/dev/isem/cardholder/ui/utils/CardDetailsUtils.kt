package dev.isem.cardholder.ui.utils

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dev.isem.cardholder.R
import dev.isem.cardholder.ui.MainActivity
import kotlinx.android.synthetic.main.amount_fragment.view.*
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*


const val NONE = 0
const val VISA = 1
const val MASTERCARD = 2
const val VISA_PREFIX = "4"
const val MASTERCARD_PREFIX = "51,52,53,54,55,"

class CardDetailsUtils {

    companion object {
        var cardNumberFlag = false
        var cardExpiryFlag = false
        var cardCvvFlag = false

        fun getCardType(cardNumber: String): Int {
            return when {
                cardNumber.substring(0, 1) == VISA_PREFIX -> VISA
                MASTERCARD_PREFIX.contains(cardNumber.substring(0, 2) + ",") -> MASTERCARD
                else -> NONE
            }
        }

        fun setCardType(
            context: Context?,
            cardTypeIcon: ImageView?,
            type: Int?
        ) {
            when (type) {
                VISA -> cardTypeIcon?.setImageDrawable(
                    ContextCompat.getDrawable(
                        context as MainActivity, R.drawable.ic_visa
                    )
                )
                MASTERCARD -> cardTypeIcon?.setImageDrawable(
                    ContextCompat.getDrawable(
                        context as MainActivity, R.drawable.ic_mastercard
                    )
                )
                NONE -> cardTypeIcon?.setImageDrawable(
                    ContextCompat.getDrawable(
                        context as MainActivity, R.drawable.ic_debit_card
                    )
                )
            }
        }

        fun isValid(cardNumber: String): Boolean {
            if (!TextUtils.isEmpty(cardNumber) && cardNumber.length >= 4)
                if (getCardType(cardNumber) == VISA && cardNumber.length == 16)
                    return true
                else if (getCardType(cardNumber) == MASTERCARD && cardNumber.length == 16)
                    return true
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
                    if (current <= validity) return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return false
        }

        fun continueCheck(continueButton: Button?) {
            continueButton?.isEnabled = cardNumberFlag && cardExpiryFlag && cardCvvFlag
        }
    }
}

fun TextInputEditText.afterTextChangedCardNumberCheck(
    context: Context?,
    cardNumberEditText: EditText?,
    cardNumberLayout: TextInputLayout?,
    cardTypeIcon: ImageView?,
    continueButton: Button?
) {
    this.addTextChangedListener(object : TextWatcher {
        private var isDelete = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            isDelete = before != 0
        }

        override fun afterTextChanged(s: Editable?) {
            val input: String = s.toString()
            val length = input.length
            val stringBuilder = StringBuilder()

            stringBuilder.append(input)

            if (length > 0 && length % 5 == 0) {
                val index = length - 1
                if (isDelete) stringBuilder.deleteCharAt(index)
                else stringBuilder.insert(index, " ")
                cardNumberEditText?.setText(stringBuilder)
                cardNumberEditText?.setSelection(cardNumberEditText.text.length)
            }

            if (length >= 4) CardDetailsUtils.setCardType(
                context,
                cardTypeIcon,
                CardDetailsUtils.getCardType(input.trim { it <= ' ' })
            )

            if (!CardDetailsUtils.isValid(input.replace(" ", ""))) {
                cardNumberLayout?.error =
                    context?.resources?.getString(R.string.error_enter_valid_card_number)
                CardDetailsUtils.cardNumberFlag = false
            } else {
                cardNumberLayout?.error = null
                CardDetailsUtils.cardNumberFlag = true
            }

            if (length == 0) {
                cardNumberLayout?.error = null
            }

            CardDetailsUtils.continueCheck(continueButton)
        }

    })
}

fun TextInputEditText.afterTextChangeCardExpiryCheck(
    context: Context?,
    expirationDateInputEditText: EditText?,
    expirationDateLayout: TextInputLayout?,
    continueButton: Button?
) {
    this.addTextChangedListener(object : TextWatcher {
        private var isDelete = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            isDelete = before != 0
        }

        override fun afterTextChanged(s: Editable?) {
            val input = s.toString()
            val length = input.length

            val stringBuilder = StringBuilder()
            stringBuilder.append(input)

            if (length > 0 && length == 3) {
                val index = length - 1

                if (isDelete) stringBuilder.deleteCharAt(index)
                else stringBuilder.insert(index, "/")

                expirationDateInputEditText?.setText(stringBuilder)
                expirationDateInputEditText?.setSelection(expirationDateInputEditText.text.length)
            }

            if (!CardDetailsUtils.isValidDate(input)) {
                expirationDateLayout?.error =
                    context?.resources?.getString(R.string.error_enter_correct_date)
                CardDetailsUtils.cardExpiryFlag = false
            } else {
                expirationDateLayout?.error = null
                CardDetailsUtils.cardExpiryFlag = true
            }

            if (length == 0) {
                expirationDateLayout?.error = null
            }

            CardDetailsUtils.continueCheck(continueButton)
        }

    })
}

fun TextInputEditText.afterTextChangedCardCvvCheck(continueButton: Button) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            CardDetailsUtils.cardCvvFlag = s?.length == 3
            CardDetailsUtils.continueCheck(continueButton)
        }
    })
}

fun EditText.amountOnTextChanged() {
    this.addTextChangedListener(object : TextWatcher {
        private var current: String = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString() != current) {
                amountTextInput.removeTextChangedListener(this)

                val cleanString: String? = s?.replace("""[$,.]""".toRegex(), "")

                val parsed = BigDecimal(cleanString ?: "0.00").setScale(2, BigDecimal.ROUND_FLOOR).divide(
                    BigDecimal(100), BigDecimal.ROUND_FLOOR
                )
                val formatted = NumberFormat.getNumberInstance(Locale.US).format(parsed)

                current = formatted
                amountTextInput.setText(formatted)
                amountTextInput.setSelection(formatted.length)

                amountTextInput.addTextChangedListener(this)
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }

    })
}
