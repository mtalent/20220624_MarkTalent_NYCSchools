package com.talent.a202220624_marktalent_nycschools.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.talent.a202220624_marktalent_nycschools.api.ApiService.Companion.SCHOOL_ITEM
import com.talent.a202220624_marktalent_nycschools.model.schools.SchoolsItem
import com.talent.a202220624_marktalent_nycschools.viewmodel.SchoolClickEvent

import com.talent.a202220624_marktalent_nycschools.viewmodel.SchoolsViewModel
import com.talent.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Setting up the models that present a list of schools and
 *  provide an option to click and to each school details.
 *  Validation is also provided.
 *
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SchoolClickEvent {

    private lateinit var bindingMain: ActivityMainBinding



    private val viewModel: SchoolsViewModel by lazy {
        ViewModelProvider(this).get(SchoolsViewModel::class.java)
    }

    private lateinit var adapterSchool: SchoolsAdapter

    /**
     *  perform basic application startup logic that should
     *  happen only once for the entire life of the activity
     *
     * @param savedInstanceState
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        initializeRecyclerView()
        initObservables()
    }

    private fun initializeRecyclerView() {
        adapterSchool = SchoolsAdapter(this)

        bindingMain.listSchool.apply {
            layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
            adapter = adapterSchool
        }
    }

    private fun initObservables() {
        viewModel.schoolResponse.observe(this) { action ->
            when (action) {
                is ResponseState.Loading -> {
                    Toast.makeText(this@MainActivity, "loading schools...", Toast.LENGTH_SHORT).show()
                }
                is ResponseState.SUCCESS<*> -> {
                    val newSchools = action.response as? List<SchoolsItem>
                    newSchools?.let {
                        adapterSchool.updateData(it)
                        Log.i("MainActivity", "initObservablesSchoolResponse $it ")
                    } ?: showError("Error at casting")
                }
                is ResponseState.ERROR -> {
                    println("|+||+||+| ~~~~~~~~~~~  ERROR ~~~~~~~~ |+||+||+|")
                    println("${action.error.localizedMessage}")
                    showError(action.error.localizedMessage)
                }
            }
        }
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this@MainActivity)
            .setTitle("Error occurred")
            .setMessage(message)
            .setNegativeButton("CLOSE") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun schoolClicked(school: SchoolsItem) {
        Intent(this@MainActivity, ScoreActivity::class.java).apply {
            putExtra(SCHOOL_ITEM, school)
            startActivity(this)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.schoolResponse.removeObservers(this)
    }
}

