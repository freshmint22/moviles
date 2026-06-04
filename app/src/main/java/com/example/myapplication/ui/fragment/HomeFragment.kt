package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.AppViewModel

class HomeFragment : Fragment() {
    private val viewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApplicationTheme {
                    val isAudioOn = viewModel.isAudioOn.collectAsState().value
                    HomeScreen(
                        isAudioOn = isAudioOn,
                        onRate = { findNavController().navigate(R.id.rateFragment) },
                        onToggleAudio = { viewModel.toggleAudio() },
                        onInstructions = { findNavController().navigate(R.id.instructionsFragment) },
                        onChallenges = { findNavController().navigate(R.id.challengesFragment) },
                        onShare = { findNavController().navigate(R.id.shareFragment) },
                        onPauseBackground = { viewModel.pauseAudioTemporarily() },
                        onResumeBackground = { viewModel.resumeAudioIfOn() },
                        onPlaySpinSound = { duration -> viewModel.playSpinSound(duration) },
                        loadRandomChallenge = { viewModel.getRandomChallengeText() },
                        loadRandomPokemonUrl = { viewModel.getRandomPokemonImage() }
                    )
                }
            }
        }
    }
}


