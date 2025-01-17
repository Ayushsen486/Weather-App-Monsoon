package com.example.monsoon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.monsoon.ui.theme.MonsoonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ViewModel= ViewModelProvider(this)[ViewModel::class.java]
        setContent {
            MonsoonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    LinearGradientBox()
                    WeatherPage(ViewModel)

                }
            }
        }
    }
}

// it is used to create the background of the app
@Preview
@Composable
fun LinearGradientBox() {
    val brush = Brush.linearGradient(
        colors = listOf(
            Color( 0xFF60EFFF),
            Color(0xFF0061FF),
            Color(0xFFD0BCFF),
            Color(0xFF6650a4),
            Color(0xFF7D5260)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = brush)
    ) {
        // Content of the Box can go here
    }
}



