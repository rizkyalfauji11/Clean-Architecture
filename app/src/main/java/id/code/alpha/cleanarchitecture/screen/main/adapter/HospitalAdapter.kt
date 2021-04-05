package id.code.alpha.cleanarchitecture.screen.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.code.alpha.cleanarchitecture.databinding.ItemHospitalBinding
import id.code.alpha.domain.model.Hospital

class HospitalAdapter(private val listener: (Hospital?) -> Unit) :
    PagedListAdapter<Hospital, HospitalAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Hospital> =
            object : DiffUtil.ItemCallback<Hospital>() {
                override fun areItemsTheSame(oldNote: Hospital, newNote: Hospital): Boolean {
                    return oldNote.name == newNote.name && oldNote.address == newNote.address
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldNote: Hospital, newNote: Hospital): Boolean {
                    return oldNote == newNote
                }
            }
    }

    inner class ViewHolder(private val binding: ItemHospitalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Hospital?, listener: (Hospital?) -> Unit) {
            with(binding) {
                name.text = data?.name
                textAddress.text = data?.address
                textBedCount.text = data?.bedCount.toString()
                textType.text = data?.type?.replace("_".toRegex(), " ")
                itemView.setOnClickListener {
                    listener(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

}