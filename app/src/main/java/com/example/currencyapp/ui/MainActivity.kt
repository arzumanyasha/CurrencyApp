package com.example.currencyapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.currencyapp.R
import com.example.currencyapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setToolbar()
        setUpNavigation()
    }

    private fun setToolbar() {
        binding.apply {
            baseChooserImageButton.setOnClickListener {
                navController.navigate(R.id.actionToCurrencyBottomSheet)
            }
            sortImageButton.setOnClickListener {
                navController.navigate(R.id.actionToSortBottomSheet)
            }
        }
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    fun updateToolbarTitle(base: String) {
        binding.baseTextView.text = base
    }
}