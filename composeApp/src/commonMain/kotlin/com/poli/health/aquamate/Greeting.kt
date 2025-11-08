package com.poli.health.aquamate

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, Aqua matter! \n${platform.name}"
    }
}