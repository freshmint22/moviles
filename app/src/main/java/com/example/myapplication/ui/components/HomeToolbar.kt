package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeToolbar(
    isAudioOn: Boolean,
    onRate: () -> Unit,
    onToggleAudio: () -> Unit,
    onInstructions: () -> Unit,
    onChallenges: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Black,
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PressableIconButton(onClick = onRate) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Calificar",
                    tint = Color(0xFFFF8A00),
                    modifier = Modifier.size(26.dp)
                )
            }
            PressableIconButton(onClick = onToggleAudio) {
                Icon(
                    imageVector = if (isAudioOn) Icons.Filled.VolumeUp else Icons.Filled.VolumeOff,
                    contentDescription = if (isAudioOn) "Audio encendido" else "Audio apagado",
                    tint = if (isAudioOn) Color(0xFFFF8A00) else Color(0xFFFFC266),
                    modifier = Modifier.size(26.dp)
                )
            }
            PressableIconButton(onClick = onInstructions) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Instrucciones",
                    tint = Color(0xFFFF8A00),
                    modifier = Modifier.size(26.dp)
                )
            }
            PressableIconButton(onClick = onChallenges) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Retos",
                    tint = Color(0xFFFF8A00),
                    modifier = Modifier.size(26.dp)
                )
            }
            PressableIconButton(onClick = onShare) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Compartir",
                    tint = Color(0xFFFF8A00),
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}


