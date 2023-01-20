package com.rahulghag.conduit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import com.rahulghag.conduit.databinding.ActivityMainBinding
import com.rahulghag.conduit.domain.usecases.GetUserAuthStateUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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

@HiltViewModel
class MainViewModel @Inject constructor(
    getUserAuthStateUseCase: GetUserAuthStateUseCase
) : ViewModel() {
    private val _isUserAuthenticated = MutableLiveData<Boolean>()
    val isUserAuthenticated: LiveData<Boolean> = _isUserAuthenticated

    init {
        _isUserAuthenticated.postValue(getUserAuthStateUseCase.invoke())
    }
}

// TODO: Add navigation animations
// TODO: Refactor article listing repository, use cases
// TODO: Re-design create article screen
// TODO: Replace custom toolbars with MaterialToolbar
// TODO: Add logout use case