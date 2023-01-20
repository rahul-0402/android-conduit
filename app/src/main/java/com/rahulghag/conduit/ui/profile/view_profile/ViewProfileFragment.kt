package com.rahulghag.conduit.ui.profile.view_profile

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahulghag.conduit.databinding.FragmentViewProfileBinding
import com.rahulghag.conduit.ui.articles.list.ArticleAdapter
import com.rahulghag.conduit.ui.articles.list.ArticleListFragmentDirections
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
            imageButtonBack.setOnClickListener {
                findNavController().popBackStack()
            }

            val layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration =
                DividerItemDecoration(requireActivity(), layoutManager.orientation)
            recyclerViewArticleList.layoutManager = layoutManager
            recyclerViewArticleList.addItemDecoration(dividerItemDecoration)
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
                            articleAdapter = ArticleAdapter(
                                list = articles,
                                onArticleClick = ::navigateToArticleDetailsScreen
                            )
                            binding.recyclerViewArticleList.adapter = articleAdapter
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