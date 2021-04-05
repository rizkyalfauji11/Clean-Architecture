package id.code.alpha.cleanarchitecture.screen.dialog.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.code.alpha.cleanarchitecture.R
import id.code.alpha.cleanarchitecture.databinding.ItemFilterBinding

class FilterAdapter(private val listener: (String?) -> Unit) :
    PagedListAdapter<String, FilterAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldNote: String, newNote: String): Boolean {
                    return oldNote == newNote && oldNote == newNote
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldNote: String, newNote: String): Boolean {
                    return oldNote == newNote
                }
            }
    }

    inner class ViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String?, listener: (String?) -> Unit) {
            with(binding) {
                textFilter.text =
                    if (data.isNullOrEmpty()) {
                        itemView.resources.getString(R.string.other)
                    } else {
                        data.replace("_".toRegex(), " ")
                    }

                itemView.setOnClickListener {
                    listener(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

}