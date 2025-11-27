package com.poli.health.aquamate.ui.theme

object AquaMateStrings {

    object Auth {
        const val TITLE = "Welcome to AquaMate"
        const val EMAIL_LABEL = "Email"
        const val PASSWORD_LABEL = "Password"
        const val SIGN_IN_BUTTON = "Sign In"
        const val SIGN_UP_TEXT = "Don't have an account? "
        const val SIGN_UP_LINK = "Sign Up"
        const val OR_DIVIDER = "or"
        const val SHOW_PASSWORD = "Show"
        const val HIDE_PASSWORD = "Hide"
        const val AUTHENTICATING = "Authenticating..."
    }

    object Common {
        const val LOADING = "Loading..."
        const val ERROR = "Error"
        const val SUCCESS = "Success"
        const val CANCEL = "Cancel"
        const val OK = "OK"
    }

    object Intake {
        const val VOLUME_MUST_BE_POSITIVE = "Volume must be greater than 0"
        const val VOLUME_EXCEEDS_LIMIT = "Volume cannot exceed 2000ml"
        const val NO_RECORDS_TO_DELETE = "No records to delete"
        const val ERROR_LOADING_DATA = "Error loading data"
        const val ERROR_REGISTERING_INTAKE = "Error registering intake"
        const val ERROR_DELETING_RECORD = "Error deleting record"
        const val RECORD_DELETED = "Record deleted"
        const val VOLUME_REGISTERED = "ml registered!"
    }
}
