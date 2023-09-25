package com.mewz.themoviebooking.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.databinding.FragmentCancellationPolicyBinding

class CancellationPolicyFragment : DialogFragment() {

    private lateinit var binding: FragmentCancellationPolicyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_cancellation_policy, container, false)

        binding = FragmentCancellationPolicyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

}