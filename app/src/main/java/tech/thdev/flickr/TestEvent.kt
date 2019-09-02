package tech.thdev.flickr

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch

object TestEvent {

    val channel = Channel<Boolean>()

    private suspend fun send(channel: SendChannel<Boolean>, success: Boolean) {
        channel.send(success)
    }

    fun loadSuccess() = GlobalScope.launch {
        send(channel, true)
    }

    fun loadFail() = GlobalScope.launch {
        send(channel, false)
    }
}