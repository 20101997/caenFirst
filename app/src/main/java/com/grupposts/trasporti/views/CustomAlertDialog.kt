package com.grupposts.trasporti.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.DialogCustomAlertBinding

class CustomAlertDialog : DialogFragment(R.layout.dialog_custom_alert) {

    private lateinit var binding: DialogCustomAlertBinding
    private val navArgs: CustomAlertDialogArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Widget_App_CustomAlertDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogCustomAlertBinding.bind(view)

        binding.tvTitle.text = navArgs.title
        binding.tvMessage.text = navArgs.message

        binding.ibClose.setOnClickListener {
            dismiss()
        }
    }
}