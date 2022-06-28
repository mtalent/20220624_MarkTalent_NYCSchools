package com.talent.a202220624_marktalent_nycschools.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talent.a202220624_marktalent_nycschools.model.Repository
import com.talent.a202220624_marktalent_nycschools.view.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  ViewModel is a class that is responsible for
 *  preparing and managing the data for an Activity
 *
 *   It also handles the communication of the Activity
 *   with the rest of the application
 *
 * @property repository
 * @property ioDispatcher
 * @property coroutineScope
 */
@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val repository: Repository,
    private val ioDispatcher: CoroutineDispatcher,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val _schoolResponse = MutableLiveData<ResponseState>()
    val schoolResponse: MutableLiveData<ResponseState>
        get() = _schoolResponse

    private val _schoolScoreResponse = MutableLiveData<ResponseState>(ResponseState.Loading)
    val schoolSatResponse: MutableLiveData<ResponseState>
        get() = _schoolScoreResponse

    init {
        getSchoolList()
    }

    private fun getSchoolList() {
        coroutineScope.launch(ioDispatcher) {
            repository.getSchool().collect {
                _schoolResponse.postValue(it)
            }
        }
    }

    fun getScoreList() {
        coroutineScope.launch(ioDispatcher) {
            repository.getScore().collect {
                _schoolScoreResponse.postValue(it)
            }
        }
    }
}