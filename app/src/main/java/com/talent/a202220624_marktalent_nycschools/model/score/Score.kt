package com.talent.a202220624_marktalent_nycschools.model.score

import com.google.gson.annotations.SerializedName

data class Score(
    val dbn: String,
    @SerializedName("tests_taken")
    val num_of_sat_test_takers: String,
    @SerializedName("reading")
    val sat_critical_reading_avg_score: String,
    @SerializedName("math")
    val sat_math_avg_score: String,
    @SerializedName("writing")
    val sat_writing_avg_score: String,
    val school_name: String
)