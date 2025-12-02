package com.poli.health.aquamate.onboarding.profile.domain.model

/**
 * Supported weight measurement units
 */
enum class WeightUnit(val abbreviation: String, val kgConversionFactor: Double) {
    KILOGRAM(abbreviation = "kg", kgConversionFactor = 1.0),
    POUND(abbreviation = "lb", kgConversionFactor = 0.453592);

    /**
     * Converts the given value in this unit to kilograms
     */
    fun toKilograms(value: Double): Double = value * kgConversionFactor

    /**
     * Converts the given value from kilograms to this unit
     */
    fun fromKilograms(valueInKg: Double): Double = valueInKg / kgConversionFactor
}
