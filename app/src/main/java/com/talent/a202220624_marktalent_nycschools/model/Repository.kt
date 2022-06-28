package com.talent.a202220624_marktalent_nycschools.model

import com.talent.a202220624_marktalent_nycschools.api.ApiService
import com.talent.a202220624_marktalent_nycschools.view.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject



interface Repository {
    fun getSchool(): Flow<ResponseState>
    fun getScore(): Flow<ResponseState>
}

/**
 *  facilitates de-coupling of the business logic
 *  and the data access layers in your application
 *
 * @property service
 */


//Implementation to get School and SAT data loaded successfully otherwise provide an error
class RepositoryImpl @Inject constructor(
    private val service: ApiService
) : Repository {

    override fun getSchool() = flow {
        emit(ResponseState.Loading)
        try {
            val response = service.getSchoolList()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResponseState.SUCCESS(it))
                } ?: throw Exception("Error Null")
            } else {
                throw Exception("Error Failure")
            }

        } catch (e : Exception) {
            emit(ResponseState.ERROR(e))
        }
    }

    override fun getScore() = flow {
        try {
            val response = service.getSchoolScore()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResponseState.SUCCESS(it))
                } ?: throw Exception("Error Null")
            } else {
                throw Exception("Error Failure")
            }

        } catch (e : Exception) {
            emit(ResponseState.ERROR(e))
        }
    }
}