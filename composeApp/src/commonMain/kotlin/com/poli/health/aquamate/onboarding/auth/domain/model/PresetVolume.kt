package com.poli.health.aquamate.onboarding.auth.domain.model

enum class PresetVolume(val ml: Int, val label: String) {
    SMALL(200, "200 ml"),
    MEDIUM(500, "500 ml"),
    LARGE(780, "780 ml");

    companion object {
        fun fromMl(ml: Int): PresetVolume? {
            return entries.find { it.ml == ml }
        }
    }
}
