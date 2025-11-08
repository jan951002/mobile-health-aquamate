package com.poli.health.aquamate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform