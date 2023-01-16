package com.rahulghag.conduit.features.articles.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.rahulghag.conduit.databinding.FragmentArticleDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {
    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!

    private val articlesDetailsViewModel: ArticleDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
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
            toggleButtonFollowUser.setOnClickListener {
                articlesDetailsViewModel.onEvent(ArticleDetailsUiEvent.ToggleFollowUser)
            }
            toggleButtonFavoriteArticle.setOnClickListener {
                articlesDetailsViewModel.onEvent(ArticleDetailsUiEvent.ToggleFavoriteArticle)
            }
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                articlesDetailsViewModel.uiState.collect { uiState ->
                    binding.apply {
                        if (uiState.authorName.isNotEmpty()) {
                            textViewAuthorAvatar.apply {
                                visibility = View.VISIBLE
                                text = uiState.authorName.take(1).uppercase()
                            }
                        }

                        textViewAuthorName.text = uiState.authorName

                        uiState.isFollowingAuthor?.let {
                            toggleButtonFollowUser.apply {
                                visibility = View.VISIBLE
                                isChecked = it
                            }
                        }

                        textViewPublishedDate.text = uiState.publishedDate

                        textViewArticleTitle.text = uiState.title

                        textViewArticleBody.text = uiState.body

                        uiState.isFavorite?.let {
                            toggleButtonFavoriteArticle.apply {
                                visibility = View.VISIBLE
                                isChecked = it
                            }
                        }

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
                        articlesDetailsViewModel.messageShown()
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "ArticleDetailsFragment"
    }
}