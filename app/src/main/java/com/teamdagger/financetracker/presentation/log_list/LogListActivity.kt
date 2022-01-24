package com.teamdagger.financetracker.presentation.log_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamdagger.financetracker.databinding.ActivityLogListBinding

class LogListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogListBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}