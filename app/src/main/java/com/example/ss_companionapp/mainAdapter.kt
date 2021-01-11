package com.example.ss_companionapp

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MainAdapter(val dataSet : Array<Rune>): RecyclerView.Adapter<CustomViewHolder>() {

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

        if (dataSet[position].path.exists()) {
            val myBitmap = BitmapFactory.decodeFile(dataSet[position].path.getAbsolutePath())
            holder.imageView.setImageBitmap(myBitmap)
        }


        if (dataSet[position].attackSpeed > 0)
        {
            holder.stat1.setTextColor(Color.GREEN)
        }
        else
        {
            holder.stat1.setTextColor(Color.RED)
        }
        if (dataSet[position].abilityPower > 0)
        {
            holder.stat2.setTextColor(Color.GREEN)
        }
        else
        {
            holder.stat2.setTextColor(Color.RED)
        }
        if (dataSet[position].normalAttackDmg > 0)
        {
            holder.stat3.setTextColor(Color.GREEN)
        }
        else
        {
            holder.stat3.setTextColor(Color.RED)
        }
        if (dataSet[position].heavyAttackDmg > 0)
        {
            holder.stat4.setTextColor(Color.GREEN)
        }
        else
        {
            holder.stat4.setTextColor(Color.RED)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size;
    }
}

class CustomViewHolder(v: View): RecyclerView.ViewHolder(v){
    val titleView : TextView = v.findViewById(R.id.textView)
    val imageView : ImageView = v.findViewById(R.id.imageView3)
    val stat1 : TextView = v.findViewById(R.id.statIncrease1)
    val stat2 : TextView = v.findViewById(R.id.statIncrease2)
    val stat3 : TextView = v.findViewById(R.id.statDecrease1)
    val stat4 : TextView = v.findViewById(R.id.statDecrease2)
}