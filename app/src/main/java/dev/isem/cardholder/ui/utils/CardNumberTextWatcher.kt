package dev.isem.cardholder.ui.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import dev.isem.cardholder.R
import dev.isem.cardholder.ui.CardDetailsFragment
import kotlinx.android.synthetic.main.card_details_fragment.view.*

class CardNumberTextWatcher(
    private val context: Context,
    private val cardNumberEditText: EditText?,
    private val cardNumberLayout: TextInputLayout?,
    private val continueButton: Button,
    private val cardType: CardType
) : TextWatcher {

    companion object {
        var continueButtonEnablingFlag = false
    }

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

        if (length >= 4) cardType.setCardType(
            CardUtils.getCardType(input.trim { it <= ' ' })
        )

        if (!CardUtils.isValid(input.replace(" ", ""))) {
            cardNumberLayout?.error =
                context.resources.getString(R.string.error_enter_valid_card_number)
            continueButtonEnablingFlag = false
        } else {
            cardNumberLayout?.error = null
            continueButtonEnablingFlag = true
        }

        if (length == 0) {
            cardNumberLayout?.error = null
        }

        CardUtils.continueCheck(continueButton)
    }

    interface CardType {
        fun setCardType(type: Int)
    }
}
