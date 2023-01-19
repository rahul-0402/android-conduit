package com.rahulghag.conduit.ui.auth.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.rahulghag.conduit.databinding.FragmentSignUpBinding
import com.rahulghag.conduit.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        collectState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        binding.apply {
            editTextEmail.doAfterTextChanged {
                signUpViewModel.onEvent(SignUpUiEvent.EmailChanged(it.toString()))
            }
            editTextPassword.doAfterTextChanged {
                signUpViewModel.onEvent(SignUpUiEvent.PasswordChanged(it.toString()))
            }
            editTextUsername.doAfterTextChanged {
                signUpViewModel.onEvent(SignUpUiEvent.UsernameChanged(it.toString()))
            }
            buttonNavigateToSignIn.setOnClickListener {
                it.hideKeyboard()
                navigateToSignInScreen()
            }
            buttonSignUp.setOnClickListener {
                it.hideKeyboard()
                signUpViewModel.onEvent(SignUpUiEvent.SignUp)
            }
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.uiState.collect { uiState ->
                    binding.progressBar.apply {
                        if (uiState.isLoading) {
                            this.visibility = View.VISIBLE
                        } else {
                            this.visibility = View.GONE
                        }
                    }
                    uiState.message?.let {
                        Toast.makeText(
                            requireActivity(),
                            it.asString(requireActivity()),
                            Toast.LENGTH_SHORT
                        ).show()
                        signUpViewModel.messageShown()
                    }
                    if (uiState.isSignUpSuccessful) {
                        navigateToArticleListScreen()
                    }
                }
            }
        }
    }

    private fun navigateToSignInScreen() {
        val action =
            SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
        findNavController().navigate(action)
    }

    private fun navigateToArticleListScreen() {
        val action =
            SignUpFragmentDirections.actionSignUpFragmentToArticleListFragment()
        findNavController().navigate(action)
    }

    companion object {
        private const val TAG = "SignUpFragment"
    }
}