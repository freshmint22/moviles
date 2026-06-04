package com.example.myapplication.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.ToneGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

class AudioController {
    private val backgroundTone = ToneGenerator(AudioManager.STREAM_MUSIC, 70)
    private var backgroundJob: Job? = null

    fun startBackground(scope: CoroutineScope) {
        if (backgroundJob?.isActive == true) return
        backgroundJob = scope.launch {
            while (isActive) {
                backgroundTone.startTone(ToneGenerator.TONE_PROP_BEEP, 200)
                delay(900)
            }
        }
    }

    fun stopBackground() {
        backgroundJob?.cancel()
        backgroundJob = null
        backgroundTone.stopTone()
    }

    fun playSpin(durationMs: Long) {
        val sampleRate = 22050
        val totalSamples = (durationMs * sampleRate / 1000L).toInt().coerceAtLeast(sampleRate / 4)
        val buffer = ShortArray(totalSamples)

        for (i in buffer.indices) {
            val t = i.toDouble() / sampleRate
            val noise = (Random.nextFloat() * 2f - 1f)
            val carrier = sin(2.0 * PI * 180.0 * t).toFloat()
            val envelope = (1f - (i.toFloat() / buffer.size)).coerceAtLeast(0.2f)
            val sample = (noise * 0.6f + carrier * 0.4f) * envelope
            buffer[i] = (sample * Short.MAX_VALUE).toInt().toShort()
        }

        val track = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(buffer.size * 2)
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()

        track.write(buffer, 0, buffer.size)
        track.play()
        track.setNotificationMarkerPosition(buffer.size)
        track.setPlaybackPositionUpdateListener(object : AudioTrack.OnPlaybackPositionUpdateListener {
            override fun onMarkerReached(track: AudioTrack) {
                track.stop()
                track.release()
            }

            override fun onPeriodicNotification(track: AudioTrack) = Unit
        })
    }
}
