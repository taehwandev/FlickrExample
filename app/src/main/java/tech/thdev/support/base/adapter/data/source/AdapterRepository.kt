package tech.thdev.support.base.adapter.data.source

import androidx.annotation.MainThread
import tech.thdev.support.base.adapter.data.AdapterItem

class AdapterRepository {

    /**
     * Index 범위를 벗어나는지 확인하기 위한 용도
     */
    @MainThread
    private fun Int.getIndexOfBounds() =
        this.takeIf { this in 0..itemCount }

    private val list = mutableListOf<AdapterItem>()

    val itemCount: Int
        get() = list.size

    @MainThread
    fun getItemViewType(position: Int): Int =
        position.getIndexOfBounds()?.let {
            list[it].viewType
        } ?: -1

    @MainThread
    fun getItem(position: Int): Any? =
        position.getIndexOfBounds()?.let {
            list[it].item
        }

    @MainThread
    fun addItems(viewType: Int, item: List<Any>) {
        item.forEach {
            addItem(viewType, it)
        }
    }

    @MainThread
    fun addItem(viewType: Int, item: Any = true) {
        list.add(AdapterItem(viewType, item))
    }

    @MainThread
    fun removeAt(position: Int) {
        position.getIndexOfBounds()?.let {
            list.removeAt(it)
        }
    }

    @MainThread
    fun clear() {
        list.clear()
    }
}