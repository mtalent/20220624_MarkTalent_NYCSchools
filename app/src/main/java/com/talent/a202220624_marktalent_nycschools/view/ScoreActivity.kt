package com.talent.a202220624_marktalent_nycschools.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.talent.a202220624_marktalent_nycschools.api.ApiService.Companion.SCHOOL_ITEM
import com.talent.a202220624_marktalent_nycschools.model.schools.SchoolsItem
import com.talent.a202220624_marktalent_nycschools.model.score.Score
import com.talent.a202220624_marktalent_nycschools.viewmodel.SchoolsViewModel
import com.talent.databinding.ActivityScoreBinding
import dagger.hilt.android.AndroidEntryPoint


//Activity that provides detail information about each school plus SAT scores if avaiable
//if no SAT information present, element does not display.
//validation of data is also coded.
@AndroidEntryPoint
class ScoreActivity : AppCompatActivity() {
    private lateinit var bindingScore: ActivityScoreBinding

    private val viewModel: SchoolsViewModel by lazy {
        ViewModelProvider(this).get(SchoolsViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingScore = ActivityScoreBinding .inflate(layoutInflater)
        setContentView(bindingScore.root)

        intent.apply {
            val school = getParcelableExtra<SchoolsItem>(SCHOOL_ITEM)

            bindingScore.tvSchoolName.text = school?.school_name
            bindingScore.tvAddress.text = school?.primary_address_line_1
            bindingScore.tvEmail.text = school?.school_email
            bindingScore.tvWebsite.text = school?.website
            bindingScore.tvOverview.text = school?.overview_paragraph

            initObservables(school?.dbn)
        }

        bindingScore.backButton.setOnClickListener{
            onBackPressed()
        }
    }

    private fun initObservables(schDbn: String?) {
        viewModel.schoolSatResponse.observe(this) { action ->
            when (action) {
                is ResponseState.Loading -> {
                    viewModel.getScoreList()
                }
                is ResponseState.SUCCESS<*> -> {
                    val newSchools = action.response as? List<Score>
                    newSchools?.let {
                        Log.i("MainActivity2", "initObservablesSAT: $it ")
                        schDbn?.let { schoolDbn ->
                            populateSatDetails(it, schoolDbn)
                        } ?: showError("Error at school dbn null")
                    } ?: showError("Error at casting SAT")
                }
                is ResponseState.ERROR -> {
                    showError(action.error.localizedMessage)
                }
            }
        }
    }

    private fun populateSatDetails(satDetails: List<Score>, schDbn: String) {
        satDetails.firstOrNull { it.dbn == schDbn }?.let {
            if (it.mathAvg.isEmpty()) {
                bindingScore.scoreInfo.visibility = View.INVISIBLE
            } else {
                bindingScore.scoreInfo.visibility = View.VISIBLE
            }

            bindingScore.tvMathScores.text = it.mathAvg
            bindingScore.tvReadingScores.text = it.readingAvg
            bindingScore.tvWritingScores.text = it.writingAvg
        }
    }

    private fun showError(message: String) {
        AlertDialog.Builder(baseContext)
            .setTitle("Error occurred")
            .setMessage(message)
            .setNegativeButton("CLOSE") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onStop() {
        super.onStop()
        viewModel.schoolSatResponse.removeObservers(this)
    }
}