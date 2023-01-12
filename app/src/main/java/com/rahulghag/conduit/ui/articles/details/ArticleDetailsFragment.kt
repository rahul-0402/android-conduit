package com.rahulghag.conduit.ui.articles.details

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
import com.rahulghag.conduit.R
import com.rahulghag.conduit.common.CircularTextDrawable
import com.rahulghag.conduit.common.TextColorGenerator
import com.rahulghag.conduit.databinding.FragmentArticleDetailsBinding
import com.rahulghag.conduit.domain.models.Article
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
            textViewFollowAuthor.setOnClickListener {
                if (articlesDetailsViewModel.isUserAuthenticated()) {
                    articlesDetailsViewModel.onEvent(ArticleDetailsUiEvent.FollowAuthor)
                } else {
                    navigateToSignInScreen()
                }
            }
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                articlesDetailsViewModel.uiState.collect { uiState ->
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
                        articlesDetailsViewModel.messageShown()
                    }
                    uiState.article?.let { article ->
                        loadArticleDetails(article)
                    }
                }
            }
        }
    }

    private fun loadArticleDetails(article: Article) {
        binding.apply {
            container.visibility = View.VISIBLE

            val generator = TextColorGenerator.MATERIAL
            val color = generator.getColor(article.author.username)
            val circularTextDrawable = CircularTextDrawable.builder().round()
            val drawable = circularTextDrawable.build(
                article.author.username.uppercase()[0].toString(), color
            )
            imageViewAuthorAvatar.setImageDrawable(drawable)

            textViewAuthorName.text = article.author.username

            textViewPublishedDate.text = article.createdAt

            textViewArticleTitle.text = article.title

            textViewArticle.text = article.body

            if (article.author.isFollowing) {
                textViewFollowAuthor.text = getString(R.string.following)
            } else {
                textViewFollowAuthor.text = getString(R.string.follow)
            }
        }
    }

    private fun navigateToSignInScreen() {
        val action = ArticleDetailsFragmentDirections.actionGlobalSignInFragment()
        findNavController().navigate(action)
    }

    companion object {
        private const val TAG = "ArticleDetailsFragment"
    }
}