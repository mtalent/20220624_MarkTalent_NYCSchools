package com.talent.a202220624_marktalent_nycschools.api

import com.talent.a202220624_marktalent_nycschools.model.schools.SchoolsItem
import com.talent.a202220624_marktalent_nycschools.model.score.Score
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(ENDPOINT_SCHOOLS)
    suspend fun getSchoolList() : Response<List<SchoolsItem>>

    @GET(ENDPOINT_SCORE)
    suspend fun getSchoolScore() : Response<List<Score>>

    companion object {
        const val BASE_URL = "https://data.cityofnewyork.us/"
        const val ENDPOINT_SCHOOLS = "resource/s3k6-pzi2.json"
        const val ENDPOINT_SCORE = "resource/f9bf-2cp4.json"

        const val SCHOOL_ITEM = "SCHOOL_ITEM"
    }

}