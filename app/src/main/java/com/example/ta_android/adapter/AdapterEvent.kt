package com.example.ta_android.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ta_android.data.ListEventResponse
import com.example.ta_android.databinding.ItemRowsEventBinding

class AdapterEvent (private val listEvent : List<ListEventResponse>): RecyclerView.Adapter<AdapterEvent.ViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(var binding : ItemRowsEventBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowsEventBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val  event = listEvent[position]
        with(holder.binding){
            Glide.with(root.context)
                .load(event.imageUrl)
                .into(imageEvent)
            tvEventTitle.text = event.title_name
            tvEventDescription.text = event.address_event
            tvEventTimeStart!!.text = event.start_register.toString()
            tvEventTimeLast!!.text = event.end_register.toString()
            root.setOnClickListener { onItemClickCallback.onItemClicked(listEvent[position]) }
        }
    }

    override fun getItemCount(): Int = listEvent.size


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListEventResponse)
    }
}