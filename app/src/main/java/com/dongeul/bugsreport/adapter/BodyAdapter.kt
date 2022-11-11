package com.dongeul.bugsreport.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongeul.bugsreport.R
import com.dongeul.bugsreport.databinding.ItemBodyBinding
import com.dongeul.bugsreport.model.BodyItem
import com.dongeul.bugsreport.util.Util.getBitmapFromURL

class BodyAdapter(val items: List<BodyItem>) : RecyclerView.Adapter<BodyAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun OnItemClick(url: String)
    }


    inner class ViewHolder(val binding: ItemBodyBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBodyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            botyTitle.text = items[position].displayTitle
            bodyDes.text = items[position].extract
            bodyImage.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}