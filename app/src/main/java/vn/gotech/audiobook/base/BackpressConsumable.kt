package vn.gotech.audiobook.base

interface BackpressConsumable {

    fun onBackPressConsumed(): Boolean
}