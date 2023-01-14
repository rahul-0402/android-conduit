package com.rahulghag.conduit.common.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.rahulghag.conduit.R
import com.rahulghag.conduit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupNavGraph()
    }

    private fun setupNavGraph() {
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        mainViewModel.isUserAuthenticated.observe(this) { isUserAuthenticated ->
            if (isUserAuthenticated) {
                graph.setStartDestination(R.id.articleListFragment)
            } else {
                graph.setStartDestination(R.id.signInFragment)
            }
            navHostFragment.navController.graph = graph
        }
    }
}