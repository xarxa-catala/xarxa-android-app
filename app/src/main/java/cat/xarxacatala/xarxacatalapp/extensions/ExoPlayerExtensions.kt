package cat.xarxacatala.xarxacatalapp.extensions

import com.google.android.exoplayer2.BasePlayer

fun BasePlayer.play() {
    this.playWhenReady = true
}

fun BasePlayer.pause() {
    this.playWhenReady = false
}

fun BasePlayer.replay() {
    seekTo(currentPosition - 10000)
}

fun BasePlayer.forward() {
    seekTo(currentPosition + 10000)
}