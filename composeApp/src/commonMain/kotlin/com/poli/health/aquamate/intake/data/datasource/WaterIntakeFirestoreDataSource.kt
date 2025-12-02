package com.poli.health.aquamate.intake.data.datasource

import com.poli.health.aquamate.intake.data.error.IntakeFirestoreErrorHandler
import com.poli.health.aquamate.intake.data.model.DailyIntakeEntity
import com.poli.health.aquamate.intake.domain.exception.IntakeException
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.serializer

internal class WaterIntakeFirestoreDataSource(
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher,
    private val errorHandler: IntakeFirestoreErrorHandler
) : WaterIntakeRemoteDataSource {

    companion object {
        private const val COLLECTION_INTAKE = "intake"
    }

    override suspend fun saveDailyIntake(userId: String, entity: DailyIntakeEntity): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                firestore.collection(COLLECTION_INTAKE)
                    .document(userId)
                    .collection("records")
                    .document(entity.date)
                    .set(entity)
                Result.success(Unit)
            } catch (e: Exception) {
                val intakeError = errorHandler.mapError(e)
                Result.failure(IntakeException(intakeError, e))
            }
        }
    }

    override suspend fun getDailyIntake(userId: String, date: String): Result<DailyIntakeEntity?> {
        return withContext(ioDispatcher) {
            try {
                val document = firestore.collection(COLLECTION_INTAKE)
                    .document(userId)
                    .collection("records")
                    .document(date)
                    .get()

                val entity = if (document.exists) {
                    document.data(serializer<DailyIntakeEntity>())
                } else {
                    null
                }
                Result.success(entity)
            } catch (e: Exception) {
                val intakeError = errorHandler.mapError(e)
                Result.failure(IntakeException(intakeError, e))
            }
        }
    }

    override suspend fun getDailyIntakesForRange(
        userId: String,
        startDate: String,
        endDate: String
    ): Result<List<DailyIntakeEntity>> {
        return withContext(ioDispatcher) {
            try {
                val query = firestore.collection(COLLECTION_INTAKE)
                    .document(userId)
                    .collection("records")
                    .where { "date" greaterThanOrEqualTo startDate }
                    .where { "date" lessThanOrEqualTo endDate }

                val snapshot = query.get()
                val entities = snapshot.documents.mapNotNull { doc ->
                    if (doc.exists) {
                        doc.data(serializer<DailyIntakeEntity>())
                    } else {
                        null
                    }
                }
                Result.success(entities)
            } catch (e: Exception) {
                val intakeError = errorHandler.mapError(e)
                Result.failure(IntakeException(intakeError, e))
            }
        }
    }
}
