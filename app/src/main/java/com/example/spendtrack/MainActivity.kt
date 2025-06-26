package com.example.spendtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spendtrack.Screen.MainScreen
import com.example.spendtrack.data.SpendViewModel


import com.example.spendtrack.ui.theme.SpendTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
           val spendViewModel: SpendViewModel = viewModel()
            SpendTrackTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    MainScreen(spendViewModel)
                }
            }
        }
    }


