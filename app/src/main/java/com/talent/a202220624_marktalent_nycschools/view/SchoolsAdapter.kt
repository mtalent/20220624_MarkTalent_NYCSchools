package com.talent.a202220624_marktalent_nycschools.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talent.a202220624_marktalent_nycschools.model.schools.SchoolsItem
import com.talent.a202220624_marktalent_nycschools.viewmodel.SchoolClickEvent
import com.talent.databinding.SchoolItemBinding

//Adapter to update data
//creating viewHolders
//And bind the viewHolder
class SchoolsAdapter(
    private val onSchoolClicked: SchoolClickEvent,
    private val items: MutableList<SchoolsItem> = mutableListOf()
) : RecyclerView.Adapter<SchoolsAdapter.SchoolsViewHolder>() {

    class SchoolsViewHolder(val binding: SchoolItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newSchools: List<SchoolsItem>) {
        items.addAll(newSchools)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolsViewHolder {
        return SchoolsViewHolder(
           SchoolItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SchoolsViewHolder, position: Int) {
        holder.binding.tvSchoolName.text = items[position].school_name
        holder.binding.tvSchoolLocation.text = items[position].neighborhood
        holder.binding.tvSchoolPhone.text = items[position].postcode


        holder.binding.cardView.setOnClickListener {
            onSchoolClicked.schoolClicked(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}