package dev.isem.cardholder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dev.isem.cardholder.*
import dev.isem.cardholder.ui.utils.*
import dev.isem.cardholder.ui.utils.CardNumberTextWatcher.CardType
import kotlinx.android.synthetic.main.card_details_fragment.*

class CardDetailsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    companion object {
        var continueButtonEnablingFlag = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.card_details_fragment_title)
        return inflater.inflate(R.layout.card_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cardNumberInputEditText?.addTextChangedListener(
            context?.let {
                CardNumberTextWatcher(
                    it,
                    cardNumberInputEditText,
                    cardNumberLayout,
                    continueButton,
                    object : CardType {
                        override fun setCardType(type: Int) {
                            when (type) {
                                VISA -> cardTypeIcon.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        activity as MainActivity, R.drawable.ic_visa
                                    )
                                )
                                MASTERCARD -> cardTypeIcon.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        activity as MainActivity, R.drawable.ic_mastercard
                                    )
                                )
                                NONE -> cardTypeIcon.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        activity as MainActivity, R.drawable.ic_debit_card
                                    )
                                )
                            }
                        }
                    })
            }
        )

        expirationDateInputEditText?.addTextChangedListener(
            context?.let {
                CardExpiryTextWatcher(
                    it,
                    expirationDateInputEditText,
                    expirationDateLayout,
                    continueButton
                )
            }
        )

        cvvInputEditText?.afterTextChangedContinueCheck(continueButton)

        continueButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_cardDetailsFragment_to_amountFragment
            )
        }
    }
}
