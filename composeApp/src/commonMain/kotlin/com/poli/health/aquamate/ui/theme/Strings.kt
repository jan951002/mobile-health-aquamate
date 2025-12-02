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
        const val TITLE = "Water Tracker"
        const val GREETING = "Hi aqua buddy!"
        const val LOGOUT = "Logout"
        const val REGISTER_TITLE = "Register your intake"
        const val REGISTER_BUTTON = "Register"
        const val VOLUME_LABEL = "Volume"
        const val ML_UNIT = "ml"

        const val DAILY_GOAL = "Daily Goal"
        const val DAILY_PROGRESS = "Daily Progress"
        const val PROGRESS_OF = "of"
        const val GOAL_ACHIEVED = "Goal Achieved!"
        const val KEEP_GOING = "Keep going!"
        const val ALMOST_THERE = "Almost there!"
        const val GREAT_START = "Great start!"

        const val HISTORY_TITLE = "Today's History"
        const val HISTORY_EMPTY = "No intakes registered yet"
        const val DELETE_CONFIRMATION = "Delete this intake?"

        const val WEEKLY_STATS_TITLE = "Weekly Stats"
        const val WEEKLY_AVERAGE = "Weekly Average"
        const val DAYS_ACHIEVED = "Days with goal achieved"
        const val DAYS_OF = "of"
        const val DAYS_UNIT = "days"

        const val TODAY = "Today"
        const val YESTERDAY = "Yesterday"
        const val PREVIOUS_DAY = "Previous day"
        const val NEXT_DAY = "Next day"

        const val LAST_INTAKE = "Last intake"
        const val AT_TIME = "at"

        const val VOLUME_MUST_BE_POSITIVE = "Volume must be greater than 0"
        const val VOLUME_EXCEEDS_LIMIT = "Volume cannot exceed 2000ml"
        const val NO_RECORDS_TO_DELETE = "No records to delete"
        const val ERROR_LOADING_DATA = "Error loading data"
        const val ERROR_REGISTERING_INTAKE = "Error registering intake"
        const val ERROR_DELETING_RECORD = "Error deleting record"
        const val RECORD_DELETED = "Record deleted successfully"
        const val VOLUME_REGISTERED = "ml registered successfully!"
        const val INTAKE_DELETED = "Intake deleted"
    }

    object Profile {
        const val TITLE = "Tell us about yourself"
        const val SUBTITLE = "We'll use this information to personalize your daily hydration goal"
        const val WEIGHT_LABEL = "Weight (kg)"
        const val HEIGHT_LABEL = "Height (cm)"
        const val DATE_OF_BIRTH_LABEL = "Date of Birth"
        const val GENDER_TITLE = "Gender"
        const val ACTIVITY_LEVEL_TITLE = "Activity Level"
        const val MALE = "Male"
        const val FEMALE = "Female"
        const val SEDENTARY = "Sedentary"
        const val SEDENTARY_DESCRIPTION = "Little to no exercise"
        const val MODERATE = "Moderate"
        const val MODERATE_DESCRIPTION = "Exercise 3-5 days/week"
        const val INTENSE = "Intense"
        const val INTENSE_DESCRIPTION = "Intense exercise 6-7 days/week"
        const val DAILY_GOALS_TITLE = "Your Daily Goals"
        const val WATER_GOAL_LABEL = "Water Goal"
        const val BMI_LABEL = "BMI"
        const val SAVE_PROFILE_BUTTON = "Save Profile"
        
        object Errors {
            const val WEIGHT_TOO_LOW = "Weight is too low. Minimum is 20 kg"
            const val WEIGHT_TOO_HIGH = "Weight is too high. Maximum is 300 kg"
            const val WEIGHT_INVALID = "Weight must be a positive number"
            const val HEIGHT_TOO_LOW = "Height is too low. Minimum is 100 cm"
            const val HEIGHT_TOO_HIGH = "Height is too high. Maximum is 250 cm"
            const val HEIGHT_INVALID = "Height must be a positive number"
            const val AGE_TOO_LOW = "You must be at least 18 years old to use this app"
            const val AGE_TOO_HIGH = "Age is too high. Maximum is 120 years"
            const val AGE_INVALID = "Age must be a positive number"
            const val USER_ID_BLANK = "User ID is required"
            const val BMR_INVALID = "Invalid metabolic rate value"
            const val PERMISSION_DENIED = "Permission denied. Please check your access rights"
            const val NETWORK_UNAVAILABLE = "Network unavailable. Please check your connection"
            const val USER_NOT_AUTHENTICATED = "Please sign in again to continue"
            const val DOCUMENT_NOT_FOUND = "Profile not found"
            const val REMOTE_UNKNOWN = "An unexpected error occurred. Please try again"
            const val LOCAL_READ_FAILED = "Failed to read local data"
            const val LOCAL_WRITE_FAILED = "Failed to save local data"
            const val LOCAL_UNKNOWN = "A local storage error occurred"
        }
    }
}
