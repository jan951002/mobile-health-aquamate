package com.poli.health.aquamate.onboarding.profile.domain.model

/**
 * Value object representing weight with a specific unit.
 * Internally stores value in kilograms for consistency.
 *
 * @property value The weight value
 * @property unit The unit of measurement
 */
data class Weight(
    val value: Double,
    val unit: WeightUnit = WeightUnit.KILOGRAM
) {
    /**
     * Gets the weight in kilograms, regardless of the original unit
     */
    val inKilograms: Double
        get() = unit.toKilograms(value)

    /**
     * Converts this weight to a different unit
     */
    fun convertTo(targetUnit: WeightUnit): Weight {
        if (unit == targetUnit) return this
        val valueInKg = inKilograms
        val newValue = targetUnit.fromKilograms(valueInKg)
        return Weight(newValue, targetUnit)
    }

    /**
     * Returns a formatted string representation
     */
    fun format(): String = "${value.toString().take(5)} ${unit.abbreviation}"

    companion object {
        /**
         * Creates a Weight from a value in kilograms
         */
        fun fromKilograms(kg: Double, targetUnit: WeightUnit = WeightUnit.KILOGRAM): Weight {
            val value = targetUnit.fromKilograms(kg)
            return Weight(value, targetUnit)
        }
    }
}
