package com.poli.health.aquamate.onboarding.profile.domain.model

/**
 * Supported height measurement units
 */
enum class HeightUnit(val abbreviation: String, val cmConversionFactor: Double) {
    CENTIMETER(abbreviation = "cm", cmConversionFactor = 1.0),
    INCH(abbreviation = "in", cmConversionFactor = 2.54),
    FOOT(abbreviation = "ft", cmConversionFactor = 30.48);

    /**
     * Converts the given value in this unit to centimeters
     */
    fun toCentimeters(value: Double): Double = value * cmConversionFactor

    /**
     * Converts the given value from centimeters to this unit
     */
    fun fromCentimeters(valueInCm: Double): Double = valueInCm / cmConversionFactor
}
