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

class CardExpiryTextWatcher(
    private val context: Context,
    private val expirationDateInputEditText: EditText?,
    private val expirationDateLayout: TextInputLayout?,
    private val continueButton: Button
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

        if (!CardUtils.isValidDate(input)) {
            expirationDateLayout?.error =
                context.resources.getString(R.string.error_enter_correct_date)
            continueButtonEnablingFlag = false
        } else {
            expirationDateLayout?.error = null
            continueButtonEnablingFlag = true
        }

        if (length == 0) {
            expirationDateLayout?.error = null
        }

        CardUtils.continueCheck(continueButton)
    }
}
