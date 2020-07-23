package com.example.android.shoppinglist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.shoppinglist.R
import com.example.android.shoppinglist.databinding.GroceryListItemBinding


import com.example.android.shoppinglist.models.GroceryList

class GroceryListAdapter(val clickListener: GroceryListClickListener,val context: Context) : ListAdapter<GroceryList, GroceryListAdapter.GroceryListViewHolder>(GroceryListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryListViewHolder {
        return GroceryListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GroceryListViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener,context)
    }

    class GroceryListViewHolder constructor(var binding: GroceryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(groceryList: GroceryList, groceryListClickListener: GroceryListClickListener,context: Context) {
            binding.groceryList = groceryList
            binding.clickListener = groceryListClickListener
            if (groceryList.isCompleted()){

                binding.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.greyOut))
            }else{

                binding.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GroceryListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GroceryListItemBinding.inflate(layoutInflater)
                return GroceryListViewHolder(binding)
            }
        }
    }

    companion object GroceryListDiffCallback: DiffUtil.ItemCallback<GroceryList>(){
        override fun areItemsTheSame(oldItem: GroceryList, newItem: GroceryList): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GroceryList, newItem: GroceryList): Boolean {
            return oldItem.listName == newItem.listName
        }

    }

    interface GroceryListClickListener {
        fun onDelete(groceryList: GroceryList) {

        }

        fun onClick(groceryList: GroceryList) {

        }
    }

}