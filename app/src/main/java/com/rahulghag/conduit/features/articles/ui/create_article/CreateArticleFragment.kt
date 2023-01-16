package com.rahulghag.conduit.features.articles.ui.create_article

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
import com.rahulghag.conduit.common.utils.hideKeyboard
import com.rahulghag.conduit.databinding.FragmentCreateArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateArticleFragment : Fragment() {
    private var _binding: FragmentCreateArticleBinding? = null
    private val binding get() = _binding!!

    private val createArticleViewModel: CreateArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateArticleBinding.inflate(inflater, container, false)
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
            imageButtonBack.setOnClickListener {
                findNavController().popBackStack()
            }
            editTextTitle.doAfterTextChanged {
                createArticleViewModel.onEvent(CreateArticleUiEvent.TitleChanged(it.toString()))
            }
            editTextDescription.doAfterTextChanged {
                createArticleViewModel.onEvent(CreateArticleUiEvent.DescriptionChanged(it.toString()))
            }
            editTextArticleBody.doAfterTextChanged {
                createArticleViewModel.onEvent(CreateArticleUiEvent.ArticleBodyChanged(it.toString()))
            }
            buttonPublish.setOnClickListener {
                it.hideKeyboard()
                createArticleViewModel.onEvent(CreateArticleUiEvent.PublishArticle)
            }
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                createArticleViewModel.uiState.collect { uiState ->
                    if (uiState.isArticlePublished) {
                        findNavController().popBackStack()
                    }
                    binding.apply {
                        if (uiState.isLoading) {
                            progressBar.visibility = View.VISIBLE
                        } else {
                            progressBar.visibility = View.GONE
                        }
                    }
                    uiState.message?.let {
                        Toast.makeText(
                            requireActivity(),
                            it.asString(requireActivity()),
                            Toast.LENGTH_SHORT
                        ).show()
                        createArticleViewModel.messageShown()
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "CreateArticleFragment"
    }
}