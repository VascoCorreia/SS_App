package com.example.ss_companionapp

import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MainAdapter(val dataSet : Array<Rune>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    //val images =


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rune_row_card, parent, false);

        return CustomViewHolder(view);
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.titleView.text = dataSet[position].name
        holder.stat1.text = "Attack Speed: " + dataSet[position].attackSpeed.toString()
        holder.stat2.text = "Ability Power :" + dataSet[position].abilityPower.toString()
        holder.stat3.text = "Normal Attack Damage: " + dataSet[position].normalAttackDmg.toString()
        holder.stat4.text = "Heavy Attack Damage : " + dataSet[position].heavyAttackDmg.toString()
        val imageBytes = Base64.decode(dataSet[position].img.data, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.imageView.setImageBitmap(decodedImage)

        /*if (dataSet[position].path.exists()) {
            val myBitmap = BitmapFactory.decodeFile(dataSet[position].path.getAbsolutePath())
            holder.imageView.setImageBitmap(myBitmap)
        }*/


        if (dataSet[position].attackSpeed > 50)
        {
            holder.stat1.setTextColor(Color.WHITE)
        }
        else
        {
            holder.stat1.setTextColor(Color.BLACK)
        }
        if (dataSet[position].abilityPower > 50)
        {
            holder.stat2.setTextColor(Color.WHITE)
        }
        else
        {
            holder.stat2.setTextColor(Color.BLACK)
        }
        if (dataSet[position].normalAttackDmg > 50)
        {
            holder.stat3.setTextColor(Color.WHITE)
        }
        else
        {
            holder.stat3.setTextColor(Color.BLACK)
        }
        if (dataSet[position].heavyAttackDmg > 50)
        {
            holder.stat4.setTextColor(Color.WHITE)
        }
        else
        {
            holder.stat4.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener{
        val titleView : TextView = itemView.findViewById(R.id.textView)
        val imageView : ImageView = itemView.findViewById(R.id.imageView3)
        val stat1 : TextView = itemView.findViewById(R.id.statIncrease1)
        val stat2 : TextView = itemView.findViewById(R.id.statIncrease2)
        val stat3 : TextView = itemView.findViewById(R.id.statDecrease1)
        val stat4 : TextView = itemView.findViewById(R.id.statDecrease2)

        init{
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION)
            {
                listener.onItemClick(position)
            }

        }
        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION)
            {
                listener.onLongItemClick(position)
            }
            return true
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onLongItemClick(position: Int)
    }

}