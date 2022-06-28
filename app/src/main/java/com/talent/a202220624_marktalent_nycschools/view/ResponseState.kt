package com.talent.a202220624_marktalent_nycschools.view

sealed class ResponseState {
    object Loading : ResponseState()
    class SUCCESS<T> (val response : T) : ResponseState()
    class ERROR (val error : Exception) : ResponseState()
}
