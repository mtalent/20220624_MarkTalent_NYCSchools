package com.talent.a202220624_marktalent_nycschools.viewmodel

import com.talent.a202220624_marktalent_nycschools.model.schools.SchoolsItem

//Interface to click on a particular school to get details about the school
interface SchoolClickEvent {
    fun schoolClicked(school: SchoolsItem)

}