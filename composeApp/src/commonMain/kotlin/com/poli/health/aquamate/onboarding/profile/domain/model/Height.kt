package com.poli.health.aquamate.onboarding.profile.domain.model

/**
 * Value object representing height with a specific unit.
 * Internally stores value in centimeters for consistency.
 *
 * @property value The height value
 * @property unit The unit of measurement
 */
data class Height(
    val value: Double,
    val unit: HeightUnit = HeightUnit.CENTIMETER
) {
    /**
     * Gets the height in centimeters, regardless of the original unit
     */
    val inCentimeters: Double
        get() = unit.toCentimeters(value)

    /**
     * Gets the height in meters
     */
    val inMeters: Double
        get() = inCentimeters / 100.0

    /**
     * Converts this height to a different unit
     */
    fun convertTo(targetUnit: HeightUnit): Height {
        if (unit == targetUnit) return this
        val valueInCm = inCentimeters
        val newValue = targetUnit.fromCentimeters(valueInCm)
        return Height(newValue, targetUnit)
    }

    /**
     * Returns a formatted string representation
     */
    fun format(): String = "${value.toString().take(5)} ${unit.abbreviation}"

    companion object {
        /**
         * Creates a Height from a value in centimeters
         */
        fun fromCentimeters(cm: Double, targetUnit: HeightUnit = HeightUnit.CENTIMETER): Height {
            val value = targetUnit.fromCentimeters(cm)
            return Height(value, targetUnit)
        }
    }
}
