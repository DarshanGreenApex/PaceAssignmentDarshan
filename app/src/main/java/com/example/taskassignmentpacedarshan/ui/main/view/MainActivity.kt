package com.example.taskassignmentpacedarshan.ui.main.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.taskassignmentpacedarshan.BR
import com.example.taskassignmentpacedarshan.R
import com.example.taskassignmentpacedarshan.common.base.BaseActivity
import com.example.taskassignmentpacedarshan.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {
    private val viewModel: MainViewModel by inject()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.navHostFragment)
        bottom_bar.onTabSelected = {
            when (it.id) {
                R.id.dashboardFragment -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.dashboardFragment)
                }
                R.id.goalsFragment -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.goalsFragment)
                }
                R.id.fractionsFragment -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.fractionsFragment)
                }
                R.id.proTradeFragment -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.proTradeFragment)
                }
                R.id.profileFragment -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.profileFragment)
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getVm(): ViewModel {
        return viewModel
    }

    override fun getBindingVariable(): Int {
        return BR.mainViewModel
    }

    override fun getLoadingView(): View? {
        return loadingView
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_bar_tabs, menu)
        bottom_bar.setupWithNavController(menu!!, navController)
        return true
    }
}