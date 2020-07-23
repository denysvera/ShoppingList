package com.example.android.shoppinglist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.shoppinglist.R
import com.example.android.shoppinglist.databinding.GroceryItemBinding

import com.example.android.shoppinglist.models.GroceryItem



class GroceryItemAdapter (val clickListener: GroceryItemClickListener, val context: Context): ListAdapter<GroceryItem, GroceryItemAdapter.GroceryItemViewHolder> (GroceryItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryItemViewHolder {
       return GroceryItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GroceryItemViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener,context)
    }
    
    class GroceryItemViewHolder constructor(var binding: GroceryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(groceryItem: GroceryItem, groceryListClickListener: GroceryItemClickListener,context: Context) {
            binding.groceryItem = groceryItem
            binding.clickListener = groceryListClickListener
            binding.checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked){
                    groceryItem.inCart = true
                    binding.layout.setBackgroundColor(getColor(context,R.color.greyOut))
                }else{
                    groceryItem.inCart = false
                    binding.layout.setBackgroundColor(getColor(context,R.color.white))
                }
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GroceryItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GroceryItemBinding.inflate(layoutInflater)
                return GroceryItemViewHolder(binding)
            }
        }
    }

    companion object GroceryItemDiffCallback: DiffUtil.ItemCallback<GroceryItem>(){
        override fun areItemsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
            return oldItem.name == newItem.name
        }

    }

    interface GroceryItemClickListener {
        fun onDelete(groceryItem: GroceryItem) {

        }

        fun onEdit(groceryItem: GroceryItem) {

        }

        fun onChecked(groceryItem: GroceryItem){

        }
    }
}