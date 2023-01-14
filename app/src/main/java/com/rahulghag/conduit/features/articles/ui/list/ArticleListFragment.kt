package com.rahulghag.conduit.features.articles.ui.list

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
import com.rahulghag.conduit.R
import com.rahulghag.conduit.databinding.FragmentArticleListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleListFragment : Fragment() {
    private var _binding: FragmentArticleListBinding? = null
    private val binding get() = _binding!!

    private val articleListViewModel: ArticleListViewModel by viewModels()

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleListBinding.inflate(inflater, container, false)
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
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_new_article -> {
                        navigateToNewArticleScreen()
                        true
                    }
                    R.id.action_profile -> {
                        navigateToProfileScreen()
                        true
                    }
                    else -> false
                }
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
                articleListViewModel.uiState.collect { uiState ->
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
                        articleListViewModel.messageShown()
                    }
                    uiState.articles?.let { articles ->
                        articleAdapter = ArticleAdapter(
                            list = articles,
                            onArticleClick = ::navigateToArticleDetailsScreen
                        )
                        binding.recyclerViewArticleList.adapter = articleAdapter
                    }
                }
            }
        }
    }

    private fun navigateToArticleDetailsScreen(slug: String) {
        val action =
            ArticleListFragmentDirections.actionArticleListFragmentToArticleDetailsFragment(
                slug
            )
        findNavController().navigate(action)
    }

    private fun navigateToNewArticleScreen() {
        val action =
            ArticleListFragmentDirections.actionArticleListFragmentToNewArticleFragment()
        findNavController().navigate(action)
    }

    private fun navigateToProfileScreen() {
        val action =
            ArticleListFragmentDirections.actionArticleListFragmentToProfileFragment()
        findNavController().navigate(action)
    }
}