package com.poli.health.aquamate.intake.domain.model

sealed class IntakeError {

    sealed class Validation : IntakeError() {
        data object VolumeTooLow : Validation()
        data object VolumeTooHigh : Validation()
        data object VolumeInvalid : Validation()
        data object UserIdBlank : Validation()
        data object NoRecordsToDelete : Validation()
    }

    sealed class Remote : IntakeError() {
        data object PermissionDenied : Remote()
        data object NetworkUnavailable : Remote()
        data object UserNotAuthenticated : Remote()
        data object DocumentNotFound : Remote()
        data object Unknown : Remote()
    }

    sealed class Local : IntakeError() {
        data object ReadFailed : Local()
        data object WriteFailed : Local()
        data object Unknown : Local()
    }
}
