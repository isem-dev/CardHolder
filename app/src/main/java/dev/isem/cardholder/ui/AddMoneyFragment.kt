package dev.isem.cardholder.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dev.isem.cardholder.R
import kotlinx.android.synthetic.main.add_money_fragment.*

class AddMoneyFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.add_money_fragment_title)
        return inflater.inflate(R.layout.add_money_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        paymentTypeItem.setOnClickListener {
            findNavController().navigate(
                R.id.action_addMoneyFragment_to_cardDetailsFragment
            )
        }
    }
}