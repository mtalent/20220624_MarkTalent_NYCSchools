package com.talent.a202220624_marktalent_nycschools.model.schools

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SchoolsItem(
    val dbn: String,
    val school_name: String,
    val overview_paragraph: String,
    val neighborhood: String,
    val location: String,
    val phone_number: String,
    val school_email: String,
    val website: String,
    val latitude: String,
    val longitude: String
): Parcelable