package com.rahulghag.conduit.features.auth.ui.sign_in

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
import com.rahulghag.conduit.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
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
                signInViewModel.onEvent(SignInUiEvent.EmailChanged(it.toString()))
            }
            editTextPassword.doAfterTextChanged {
                signInViewModel.onEvent(SignInUiEvent.PasswordChanged(it.toString()))
            }
            buttonNavigateToSignUp.setOnClickListener {
                navigateToSignUpScreen()
            }
            buttonSignIn.setOnClickListener {
                signInViewModel.onEvent(SignInUiEvent.SignIn)
            }
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signInViewModel.uiState.collect { uiState ->
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
                        signInViewModel.messageShown()
                    }
                    if (uiState.isSignInSuccessful) {
                        navigateToArticleListScreen()
                    }
                }
            }
        }
    }

    private fun navigateToSignUpScreen() {
        val action =
            SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        findNavController().navigate(action)
    }

    private fun navigateToArticleListScreen() {
        val action =
            SignInFragmentDirections.actionSignInFragmentToArticleListFragment()
        findNavController().navigate(action)
    }

    companion object {
        private const val TAG = "SignInFragment"
    }
}