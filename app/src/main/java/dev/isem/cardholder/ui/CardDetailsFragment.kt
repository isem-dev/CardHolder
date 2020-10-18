package dev.isem.cardholder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dev.isem.cardholder.R
import dev.isem.cardholder.ui.utils.afterTextChangedCardNumberCheck
import dev.isem.cardholder.ui.utils.afterTextChangedCardCvvCheck
import dev.isem.cardholder.ui.utils.afterTextChangeCardExpiryCheck
import kotlinx.android.synthetic.main.card_details_fragment.*

class CardDetailsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

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

        cardNumberInputEditText?.afterTextChangedCardNumberCheck(
            context,
            cardNumberInputEditText,
            cardNumberLayout,
            cardTypeIcon,
            continueButton
        )

        expirationDateInputEditText?.afterTextChangeCardExpiryCheck(
            context,
            expirationDateInputEditText,
            expirationDateLayout,
            continueButton
        )

        cvvInputEditText?.afterTextChangedCardCvvCheck(continueButton)

        continueButton?.setOnClickListener {
            findNavController().navigate(
                R.id.action_cardDetailsFragment_to_amountFragment
            )
        }
    }
}
