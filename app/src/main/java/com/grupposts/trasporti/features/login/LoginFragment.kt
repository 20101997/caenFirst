package com.grupposts.trasporti.features.login

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.FragmentLoginBinding
import com.grupposts.trasporti.features.MainViewModel
import com.grupposts.trasporti.utils.showError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            mainViewModel.onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.progressIndicator.hide()

        binding.btnLogin.setOnClickListener {
            val username = binding.tilUser.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            viewModel.login(username, password)
        }

        setObservers()
    }

    private fun setObservers() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner) {
                if (it)
                    binding.progressIndicator.show()
                else
                    binding.progressIndicator.hide()
            }

            error.observe(viewLifecycleOwner) {
                showError(it)
            }

            isLoginButtonEnabled.observe(viewLifecycleOwner) {
                if (it) {
                    binding.btnLogin.text = getString(R.string.login)
                    binding.btnLogin.isEnabled = true
                } else {
                    binding.btnLogin.text = ""
                    binding.btnLogin.isEnabled = false
                }
            }

            usernameError.observe(viewLifecycleOwner) {
                binding.tilUser.error = it
                binding.tilUser.isErrorEnabled = it != null
            }

            passwordError.observe(viewLifecycleOwner) {
                binding.tilPassword.error = it
                binding.tilPassword.isErrorEnabled = it != null
            }

            navigateToHome.observe(viewLifecycleOwner) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        }
    }

}