package com.poli.health.aquamate.intake.data.error

import com.poli.health.aquamate.intake.domain.model.IntakeError

internal interface IntakeFirestoreErrorHandler {
    fun mapError(exception: Exception): IntakeError.Remote
}
