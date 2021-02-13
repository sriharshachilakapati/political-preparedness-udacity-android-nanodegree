package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.LayoutElectionItemBinding
import com.example.android.politicalpreparedness.network.models.Election

typealias ElectionListener = (Election) -> Unit

class ElectionListAdapter(private val clickListener: ElectionListener) : ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) = holder.bind(getItem(position), clickListener)
}

class ElectionViewHolder private constructor(val binding: LayoutElectionItemBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LayoutElectionItemBinding.inflate(inflater, parent, false)
            return ElectionViewHolder(binding)
        }
    }

    fun bind(election: Election, onClickListener: ElectionListener) = binding.run {
        textViewElectionName.text = election.name
        textViewElectionDate.text = election.electionDay.toString()

        binding.root.setOnClickListener { onClickListener(election) }
    }
}

class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean =
            oldItem == newItem
}