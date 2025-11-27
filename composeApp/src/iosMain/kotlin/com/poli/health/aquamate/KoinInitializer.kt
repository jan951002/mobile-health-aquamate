package com.poli.health.aquamate

import com.poli.health.aquamate.di.initKoin as initKoinCommon
import org.koin.mp.KoinPlatformTools

fun initKoin() {
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        initKoinCommon()
    }
}

