package com.teamdagger.financetracker.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.synapsisid.smartdeskandroombooking.util.Util
import com.teamdagger.financetracker.data.datasources.local.FinanceDao
import com.teamdagger.financetracker.data.datasources.local.tables.UsersTable
import com.teamdagger.financetracker.databinding.ActivitySplashBinding
import com.teamdagger.financetracker.presentation.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {


    @Inject
    lateinit var financeDao: FinanceDao

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup(){
        if(Util.getUserID(this) == 0){
            GlobalScope.launch(Dispatchers.Main) {
                financeDao.createUser(
                    UsersTable(
                    0,
                    "fikrihkl",
                    "fikri123",
                    "Fikri Haikal"
                )
                )

                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                this@SplashActivity.finish()

            }
        }
    }
}