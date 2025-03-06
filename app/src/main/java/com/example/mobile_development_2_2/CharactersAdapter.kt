package com.example.mobile_development_2_2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.mobile_development_2_2.databinding.HumanCardBinding
import com.example.mobile_development_2_2.databinding.AlienCardBinding
import com.example.mobile_development_2_2.databinding.OtherCardBinding

abstract class CharacterViewHolder(private val itemBinding: ViewBinding) :
    RecyclerView.ViewHolder(itemBinding.root)
{
    abstract fun bind(character: ResultOfCharactersQueue)
}

class RickAndMortyCharacterAdapter(private val characters: ArrayList<ResultOfCharactersQueue>) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    private var loading = false

    fun isLoading(): Boolean{
        return loading
    }
    companion object {
        const val VIEW_TYPE_HUMAN : Int = 0
        const val VIEW_TYPE_ALIEN : Int = 1
        const val VIEW_TYPE_OTHER : Int = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return when(viewType){
            VIEW_TYPE_HUMAN -> {
                val itemBinding = HumanCardBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false)
                return HumanViewHolder(itemBinding)
            }
            VIEW_TYPE_ALIEN -> {
                val itemBinding = AlienCardBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false)
                return AlienViewHolder(itemBinding)
            }
            else ->{
                val itemBinding = OtherCardBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false)
                return OtherViewHolder(itemBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return characters.size + if (loading) 1 else 0
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character: ResultOfCharactersQueue = characters[position]
        holder.bind(character)
    }

    override fun getItemViewType(position: Int): Int {

        return when (characters[position].species) {
                    "Human" -> VIEW_TYPE_HUMAN
                    "Alien" -> VIEW_TYPE_ALIEN
                    else -> VIEW_TYPE_OTHER
                }
        }

    fun addCharacters(newCharacters: List<ResultOfCharactersQueue>) {
        val oldSize = characters.size
        characters.addAll(newCharacters)
        notifyItemRangeInserted(oldSize, newCharacters.size)
    }

    // Метод для управления состоянием загрузки
    fun setLoading(isLoading: Boolean) {
        loading = isLoading
        if (isLoading) {
            notifyItemInserted(characters.size)
        } else {
            notifyItemRemoved(characters.size)
        }
    }
}

    class HumanViewHolder(private val itemBinding: HumanCardBinding) :
        CharacterViewHolder(itemBinding)
    {
        override fun bind(character: ResultOfCharactersQueue){
            Glide.with(itemBinding.CharacterIcon).load(character.image).into(itemBinding.CharacterIcon)
            itemBinding.CharacterName.text = character.name
            itemBinding.CharacterStatus.text = character.status
            itemBinding.CharacterGender.text = character.gender
        }
    }

    class AlienViewHolder(private val itemBinding: AlienCardBinding) :
        CharacterViewHolder(itemBinding)
    {
        override fun bind(character: ResultOfCharactersQueue){
            Glide.with(itemBinding.CharacterIcon).load(character.image).into(itemBinding.CharacterIcon)
            itemBinding.CharacterName.text = character.name
            itemBinding.CharacterStatus.text = character.status
            itemBinding.CharacterGender.text = character.gender
        }
    }
    class OtherViewHolder(private val itemBinding: OtherCardBinding) :
        CharacterViewHolder(itemBinding)
    {
        override fun bind(character: ResultOfCharactersQueue){
            Glide.with(itemBinding.CharacterIcon).load(character.image).into(itemBinding.CharacterIcon)
            itemBinding.CharacterName.text = character.name
            itemBinding.CharacterStatus.text = character.status
            itemBinding.CharacterGender.text = character.gender
        }
    }


