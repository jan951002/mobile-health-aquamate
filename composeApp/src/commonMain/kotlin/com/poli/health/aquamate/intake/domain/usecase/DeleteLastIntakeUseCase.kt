package com.poli.health.aquamate.intake.domain.usecase

interface DeleteLastIntakeUseCase {
    suspend operator fun invoke(): Result<Unit>
}
