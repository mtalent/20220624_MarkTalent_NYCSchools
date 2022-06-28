package com.talent.a202220624_marktalent_nycschools.model.schools

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SchoolsItem(
    val academicopportunities1: String,
    val academicopportunities2: String,
    val academicopportunities3: String,
    val academicopportunities4: String,
    val academicopportunities5: String,
    val advancedplacement_courses: String,
    val borocode: String,
    val city: String,
    val dbn: String,
    val ell_programs: String,
    val language_classes: String,
    val neighborhood: String,
    val overview_paragraph: String,
    val postcode: String,
    val primary_address_line_1: String,
    val school_name: String,
    val state_code: String
) : Parcelable