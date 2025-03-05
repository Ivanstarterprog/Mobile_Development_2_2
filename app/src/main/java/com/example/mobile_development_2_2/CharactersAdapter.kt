package com.example.mobile_development_2_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

enum class CharactersType(type: String) {
    HUMAN("human"),

}

class RickAndMortyCharacterAdapter() : ListAdapter<RickAndMortyCharactersData, RecyclerView.ViewHolder>(DayDiffCallback()) {

    inner class DayViewHolderHot(view: View) : RecyclerView.ViewHolder(view) {
        val CharacterIcon: ImageView = view.findViewById<ImageView>(R.id.CharacterIcon);
        val CharacterName: TextView = view.findViewById<TextView>(R.id.CharacterName)
        val CharacterStatus: TextView = view.findViewById<TextView>(R.id.CharacterStatus)
        val CharacterGender: TextView = view.findViewById<TextView>(R.id.CharacterGender)

        fun bind(position: Int) {
            val character: RickAndMortyCharactersData = currentList[position]
            CharacterName.setText(Results.status)
            plusTxt.setText(hotDay.main.getTempAsString());
            val address = "https://openweathermap.org/img/wn/${hotDay.weather[0].icon}@2x.png"
            Glide.with(icon).load(address).into(icon)
        }
    }

    inner class DayViewHolderCold(view: View) : RecyclerView.ViewHolder(view) {
        val datetime: TextView = view.findViewById<TextView>(R.id.datetime);
        val minusTxt: TextView = view.findViewById<TextView>(R.id.txt_minus_temperature)
        val icon: ImageView = view.findViewById<ImageView>(R.id.icon)

        fun bind(position: Int) {
            val coldDay: RickAndMortyCharactersData = currentList[position]
            datetime.setText(coldDay.dt_txt)
            minusTxt.setText(coldDay.main.getTempAsString());
            val address = "https://openweathermap.org/img/wn/${coldDay.weather[0].icon}@2x.png"
            Glide.with(icon).load(address).into(icon)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val day: RickAndMortyCharactersData = currentList[position]
        if (day.main.temp > 0) {
            return VIEW_TYPE_HOT
        }
        return VIEW_TYPE_COLD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_HOT) {
            return DayViewHolderHot(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.r_item_hot,
                    parent, false
                )
            )
        }
        return DayViewHolderCold(
            LayoutInflater.from(parent.context).inflate(
                R.layout.r_item_cold,
                parent, false
            )
        )
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType == VIEW_TYPE_HOT){
            (holder as DayViewHolderHot).bind(position)
        }
        else{
            (holder as DayViewHolderCold).bind(position)
        }
    }
}

class DayDiffCallback : DiffUtil.ItemCallback<RickAndMortyCharactersData>() {
    override fun areItemsTheSame(oldItem: RickAndMortyCharactersData, newItem: RickAndMortyCharactersData): Boolean {
        return oldItem.dt_txt == newItem.dt_txt;
    }

    override fun areContentsTheSame(oldItem: RickAndMortyCharactersData, newItem: RickAndMortyCharactersData): Boolean {
        return oldItem == newItem;
    }
}