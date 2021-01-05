package com.spentwell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spentwell.databinding.SampleListItemBinding


class SampleAdapter(private val list: List<String>) :
    RecyclerView.Adapter<SampleAdapter.SampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: SampleListItemBinding =
            SampleListItemBinding.inflate(layoutInflater, parent, false)
        return SampleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.binding.name = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SampleViewHolder(val binding: SampleListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}