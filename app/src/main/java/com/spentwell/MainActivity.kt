package com.spentwell

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.spentwell.databinding.ActivityMainBinding
import com.spentwell.utils.AppUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.parentLayout

        val navHostFragment = nav_host_fragment as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)
        val navController = navHostFragment.navController

        val destination = if (AppUtils.isEarningsAllocated(this)) {
            if (AppUtils.isEarningsSet(this)) {
                R.id.dashboardFragment
            } else {
                R.id.setEarningsFragment
            }
        } else {
            R.id.earningsAllocationFragment
        }
        navGraph.startDestination = destination
        navController.graph = navGraph

    }
}