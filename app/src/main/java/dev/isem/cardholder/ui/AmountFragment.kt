package dev.isem.cardholder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dev.isem.cardholder.R
import dev.isem.cardholder.ui.utils.amountOnTextChanged
import kotlinx.android.synthetic.main.amount_fragment.*

class AmountFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.amount_fragment_title)
        return inflater.inflate(R.layout.amount_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        amountTextInput.amountOnTextChanged()

        viewModel.amountAdd.observe(viewLifecycleOwner ) { amount ->
            viewModel.setAmountAdd(amount)
        }
    }
}