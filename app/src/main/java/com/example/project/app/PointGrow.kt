package com.example.project.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.project.navigation.PointGrowRouter


@Composable
fun PointGrow(){
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        PointGrowRouter()
    }
}

