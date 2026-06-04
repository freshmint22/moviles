package com.example.myapplication.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.components.HomeToolbar
import com.example.myapplication.util.SpinUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    isAudioOn: Boolean,
    onRate: () -> Unit,
    onToggleAudio: () -> Unit,
    onInstructions: () -> Unit,
    onChallenges: () -> Unit,
    onShare: () -> Unit,
    onPauseBackground: () -> Unit,
    onResumeBackground: () -> Unit,
    onPlaySpinSound: (Long) -> Unit,
    loadRandomChallenge: suspend () -> String,
    loadRandomPokemonUrl: suspend () -> String?
) {
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(0f) }
    var isSpinning by remember { mutableStateOf(false) }
    var countdown by remember { mutableStateOf<Int?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var challengeText by remember { mutableStateOf("") }
    var pokemonUrl by remember { mutableStateOf<String?>(null) }
    val blinkTransition = rememberInfiniteTransition(label = "blink")
    val blinkAlpha by blinkTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blink-alpha"
    )

    if (showDialog) {
        ChallengeDialog(
            challengeText = challengeText,
            pokemonUrl = pokemonUrl,
            onClose = {
                showDialog = false
                onResumeBackground()
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        WoodBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeToolbar(
                isAudioOn = isAudioOn,
                onRate = onRate,
                onToggleAudio = onToggleAudio,
                onInstructions = onInstructions,
                onChallenges = onChallenges,
                onShare = onShare
            )

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .size(220.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_bottle),
                    contentDescription = "Botella",
                    modifier = Modifier
                        .size(200.dp)
                        .rotate(rotation.value)
                )
                if (countdown != null) {
                    Text(
                        text = countdown.toString(),
                        color = Color(0xFFFF8A00),
                        fontWeight = FontWeight.Bold,
                        fontSize = 38.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            if (!isSpinning) {
                Button(
                    onClick = {
                        if (isSpinning) return@Button
                        isSpinning = true
                        countdown = null
                        onPauseBackground()
                        val spinDuration = 3_500L
                        onPlaySpinSound(spinDuration)
                        scope.launch {
                            val target = SpinUtils.nextTargetRotation(rotation.value)
                            rotation.animateTo(
                                targetValue = target,
                                animationSpec = tween(
                                    durationMillis = spinDuration.toInt(),
                                    easing = LinearEasing
                                )
                            )
                            countdown = 3
                            while (countdown != null && countdown!! >= 0) {
                                delay(800)
                                val next = countdown!! - 1
                                countdown = if (next >= 0) next else null
                            }
                            challengeText = loadRandomChallenge()
                            pokemonUrl = loadRandomPokemonUrl()
                            showDialog = true
                            isSpinning = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A00)),
                    modifier = Modifier
                        .width(200.dp)
                        .alpha(blinkAlpha),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text = "Presioname",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun WoodBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.background(Color(0xFF6F4E37))) {
        val dark = Color(0xFF5C4033)
        val light = Color(0xFF8B5A2B)
        val stripeHeight = size.height / 10f
        for (i in 0..9) {
            drawRect(
                color = if (i % 2 == 0) dark else light,
                topLeft = Offset(0f, stripeHeight * i),
                size = androidx.compose.ui.geometry.Size(size.width, stripeHeight)
            )
        }
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color(0x33000000))
            )
        )
    }
}

@Composable
private fun ChallengeDialog(
    challengeText: String,
    pokemonUrl: String?,
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xEE000000), Color(0x99000000))
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(1.dp, Color.White, RoundedCornerShape(20.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(Color.Black, CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (pokemonUrl != null) {
                        AsyncImage(
                            model = pokemonUrl,
                            contentDescription = "Pokemon",
                            modifier = Modifier.size(72.dp)
                        )
                    } else {
                        Text(text = "?", color = Color.White, fontSize = 24.sp)
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = challengeText,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = onClose,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A00)),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.offset(y = 16.dp)
                ) {
                    Text(text = "Cerrar", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


