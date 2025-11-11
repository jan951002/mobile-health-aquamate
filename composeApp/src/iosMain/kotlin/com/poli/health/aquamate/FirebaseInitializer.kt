package com.poli.health.aquamate

import cocoapods.FirebaseCore.FIRApp
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun init() {
    FIRApp.configure()
}
