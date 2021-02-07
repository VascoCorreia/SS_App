package com.example.ss_companionapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BattleAdapter(val historySet: Array<History>) : RecyclerView.Adapter<BattleAdapter.BattleCustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattleAdapter.BattleCustomViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.battle_row_card, parent, false);

        return BattleCustomViewHolder(view);
    }

    override fun onBindViewHolder(holder: BattleCustomViewHolder, position: Int) {
        holder.outcome.text = historySet[position].battleOutcome
        holder.user1.text = historySet[position].user1
        holder.user2.text = historySet[position].user2
        holder.date.text = historySet[position].battleDate.toString()
    }

    override fun getItemCount(): Int {
        return historySet.size
    }

    inner class BattleCustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val outcome: TextView = itemView.findViewById(R.id.battleOutcome)
        val user1: TextView = itemView.findViewById(R.id.user1)
        val user2: TextView = itemView.findViewById(R.id.user2)
        val date: TextView = itemView.findViewById(R.id.battleDate)
    }




}

