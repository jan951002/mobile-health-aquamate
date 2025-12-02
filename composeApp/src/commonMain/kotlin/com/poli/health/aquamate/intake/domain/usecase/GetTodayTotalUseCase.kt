package com.poli.health.aquamate.intake.domain.usecase

interface GetTodayTotalUseCase {
    suspend operator fun invoke(): Int
}
