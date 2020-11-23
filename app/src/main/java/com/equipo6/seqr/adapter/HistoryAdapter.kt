package com.equipo6.seqr.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.equipo6.seqr.R
import com.equipo6.seqr.models.Tour
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter(val historyListener: HistoryListener) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var listTour = ArrayList<Tour>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))

    override fun getItemCount() = listTour.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tour = listTour[position] as Tour

        holder.tvItemArea.text = "${tour.checkpoint}"
        holder.tvItemUsername.text = tour.user

        val simpleDateFormat = SimpleDateFormat("dd/MM")
        val simpleYearFormat = SimpleDateFormat("yyyy")
        val simpleHourFormat = SimpleDateFormat("HH:mm a")

        val cal = Calendar.getInstance()
        cal.time = tour.check_in

        holder.tvItemYear.text = simpleYearFormat.format(tour.check_in)
        holder.tvItemDate.text = simpleDateFormat.format(tour.check_in)
        holder.tvItemCheckIn.text = simpleHourFormat.format(tour.check_in)

        holder.itemView.setOnClickListener {
            historyListener.onHistoryClicked(tour, position)
        }

    }

    fun updateData(data: List<Tour>) {
        listTour.clear()
        listTour.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemUsername = itemView.findViewById<TextView>(R.id.tvItemUsername)
        val tvItemDate = itemView.findViewById<TextView>(R.id.tvItemDate)
        val tvItemYear = itemView.findViewById<TextView>(R.id.tvItemYear)
        val tvItemCheckIn = itemView.findViewById<TextView>(R.id.tvItemCheckIn)
        val tvItemArea = itemView.findViewById<TextView>(R.id.tvItemArea)
    }
}