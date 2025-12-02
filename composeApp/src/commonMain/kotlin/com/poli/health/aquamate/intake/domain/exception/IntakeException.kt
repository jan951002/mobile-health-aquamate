package com.poli.health.aquamate.intake.domain.exception

import com.poli.health.aquamate.intake.domain.model.IntakeError

class IntakeException(
    val error: IntakeError,
    cause: Throwable? = null
) : Exception(error::class.simpleName, cause)
