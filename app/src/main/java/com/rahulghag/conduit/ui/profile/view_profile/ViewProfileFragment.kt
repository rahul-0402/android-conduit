package com.rahulghag.conduit.ui.profile.view_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.rahulghag.conduit.R
import com.rahulghag.conduit.databinding.FragmentViewProfileBinding
import com.rahulghag.conduit.ui.common.ArticleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ViewProfileFragment : Fragment() {
    private var _binding: FragmentViewProfileBinding? = null
    private val binding get() = _binding!!

    private val viewProfileViewModel: ViewProfileViewModel by viewModels()

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewProfileBinding.inflate(inflater, container, false)
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
            articleAdapter = ArticleAdapter(
                onArticleClick = ::navigateToArticleDetailsScreen
            )

            val menuIcon = toolbar.overflowIcon
            menuIcon?.setTint(ContextCompat.getColor(requireActivity(), R.color.black))
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            viewProfileViewModel.onEvent(ViewProfileUiEvent.ShowMyArticles)
                        }
                        1 -> {
                            viewProfileViewModel.onEvent(ViewProfileUiEvent.ShowFavoritedArticles)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })

            val layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration =
                DividerItemDecoration(requireActivity(), layoutManager.orientation)
            recyclerViewArticleList.layoutManager = layoutManager
            recyclerViewArticleList.addItemDecoration(dividerItemDecoration)
            recyclerViewArticleList.adapter = articleAdapter
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewProfileViewModel.uiState.collect { uiState ->
                    binding.apply {
                        uiState.profile?.let { profile ->
                            textViewUserAvatar.apply {
                                visibility = View.VISIBLE
                                text = profile.username.take(1).uppercase()
                            }

                            textViewName.text = profile.username

                            textViewBio.apply {
                                if (profile.bio.isNotEmpty()) {
                                    visibility = View.VISIBLE
                                    text = profile.bio
                                } else {
                                    visibility = View.INVISIBLE
                                }
                            }
                        }

                        uiState.articles?.let { articles ->
                            if (articles.isEmpty()) {
                                binding.recyclerViewArticleList.visibility = View.GONE
                                binding.textViewEmptyList.apply {
                                    visibility = View.VISIBLE
                                    text = if (uiState.selectedTabPosition == 0) {
                                        getString(R.string.you_have_not_created_any_articles)
                                    } else {
                                        getString(R.string.you_have_no_favorite_articles)
                                    }
                                }
                            } else {
                                binding.recyclerViewArticleList.visibility = View.VISIBLE
                                binding.textViewEmptyList.visibility = View.GONE
                                articleAdapter.submitList(articles)
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
                        viewProfileViewModel.messageShown()
                    }
                }
            }
        }
    }

    private fun navigateToArticleDetailsScreen(slug: String) {
        val action =
            ViewProfileFragmentDirections.actionViewProfileFragmentToArticleDetailsFragment(
                slug
            )
        findNavController().navigate(action)
    }
}