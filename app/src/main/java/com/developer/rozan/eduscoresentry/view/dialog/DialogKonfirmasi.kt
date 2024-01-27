package com.developer.rozan.eduscoresentry.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.developer.rozan.eduscoresentry.databinding.DialogKonfirmasiBinding

class DialogKonfirmasi : DialogFragment() {

    private lateinit var binding: DialogKonfirmasiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DialogKonfirmasiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val message = arguments?.getString("DescMsg");

            if (message != null && message != "") {
                binding.tvDescKonfirmasi.text = message
            }
        }

        binding.btnBatalKonfirmasi.setOnClickListener {
            dismiss()
        }

        binding.btnSubmitKonfirmasi.setOnClickListener {
            val dialogConfirmationListener = activity as DialogConfirmationListener?
            dialogConfirmationListener!!.onDialogConfirmationListener("LOGOUT")
            dismiss()
        }
    }

    interface DialogConfirmationListener {
        fun onDialogConfirmationListener(param: String?)
    }
}