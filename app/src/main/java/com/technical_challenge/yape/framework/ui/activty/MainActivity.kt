package com.technical_challenge.yape.framework.ui.activty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.technical_challenge.yape.databinding.ActivityMainBinding
import com.technical_challenge.yape.framework.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }




}

