package com.example.myapplication.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.TitleTopBar

@Composable
fun InstructionsScreen(onBack: () -> Unit) {
    val transition = rememberInfiniteTransition(label = "trophy")
    val scale by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "trophy-scale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B2B2B))
            .padding(16.dp)
    ) {
        TitleTopBar(title = "Reglas del Juego", onBack = onBack)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "¿Cómo se juega?",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Los jugadores forman un círculo y en el centro colocan el dispositivo móvil, luego tocan el botón parpadeante para girar la botella. El jugador que señale la botella debe cumplir el reto que lanza la app, de lo contrario abandona el juego.",
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¿Quién gana?",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Gana el último jugador que no abandone el juego.",
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Icon(
            imageVector = Icons.Filled.EmojiEvents,
            contentDescription = "Triunfo",
            tint = Color(0xFFFFD54F),
            modifier = Modifier
                .scale(scale)
                .padding(top = 16.dp)
        )
    }
}
