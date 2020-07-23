package com.example.android.shoppinglist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.shoppinglist.R
import com.example.android.shoppinglist.adapters.GroceryItemAdapter
import com.example.android.shoppinglist.database.AppDatabase
import com.example.android.shoppinglist.databinding.GroceryDetailsFragmentBinding
import com.example.android.shoppinglist.helpers.hideKeyboard
import com.example.android.shoppinglist.models.GroceryItem
import com.example.android.shoppinglist.models.GroceryList
import com.example.android.shoppinglist.repository.ShoppingListRepository

import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.edit_grocery_item_bottom_sheet.view.*

class GroceryDetailsFragment : Fragment(), GroceryItemAdapter.GroceryItemClickListener {

    companion object {
        fun newInstance() = GroceryDetailsFragment()
    }

    private lateinit var binding: GroceryDetailsFragmentBinding
    private lateinit var viewModel: GroceryDetailsViewModel
    private lateinit var  dialog: BottomSheetDialog
    private lateinit var groceryList: GroceryList
    private lateinit var adapter: GroceryItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.grocery_details_fragment, container, false)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val  database = AppDatabase.getDatabse(application)

        val shoppingListRepository =  ShoppingListRepository(database!!)
        val viewModelFactory = ViewModelFactory(shoppingListRepository)
        viewModel  = ViewModelProvider(this,viewModelFactory).get(GroceryDetailsViewModel::class.java)

        groceryList = GroceryDetailsFragmentArgs.fromBundle(requireArguments()).groceryList

        adapter = GroceryItemAdapter(this,requireContext())
       // binding.groceryItemsList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.groceryItemsList.adapter = adapter
        adapter.submitList(groceryList.groceryItemList)
        binding.addButton.setOnClickListener {
            if(!binding.item.text.toString().isNullOrEmpty()){
                val groceryItem = GroceryItem(binding.item.text.toString(),"1", "",false)
                groceryList.groceryItemList.add(0,groceryItem)
                binding.item.setText("")
               adapter.notifyDataSetChanged()
                viewModel.updateGroceryList(groceryList)
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.updateGroceryList(groceryList)
    }
    override fun onDelete(groceryItem: GroceryItem) {
        super.onDelete(groceryItem)
        groceryList.groceryItemList.remove(groceryItem)
        adapter.notifyDataSetChanged()
    }

    override fun onEdit(groceryItem: GroceryItem) {
        super.onEdit(groceryItem)
        val index = groceryList.groceryItemList.indexOf(groceryItem)
        val viewLayout = layoutInflater.inflate(R.layout.edit_grocery_item_bottom_sheet,null)
        dialog = BottomSheetDialog(requireActivity())
        dialog.setContentView(viewLayout)
        viewLayout.textInputLayout.editText!!.setText(groceryItem.name)
        viewLayout.quantityTextInputLayout.editText!!.setText(groceryItem.quantity)
        viewLayout.unitTextInputLayout.editText!!.setText(groceryItem.unit)
        viewLayout.unitTextInputLayout.editText!!.setOnEditorActionListener { view, actionId, keyEvent ->
            when(actionId){
                EditorInfo.IME_ACTION_DONE->{
                    hideKeyboard()
                    groceryItem.name = viewLayout.textInputLayout.editText!!.text.toString()
                    groceryItem.quantity = viewLayout.quantityTextInputLayout.editText!!.text.toString()
                    groceryItem.unit = viewLayout.unitTextInputLayout.editText!!.text.toString()

                    groceryList.groceryItemList[index] = groceryItem
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                    true
                }
                else -> false
            }
        }
        viewLayout.updateButton.setOnClickListener {
            groceryItem.name = viewLayout.textInputLayout.editText!!.text.toString()
            groceryItem.quantity = viewLayout.quantityTextInputLayout.editText!!.text.toString()
            groceryItem.unit = viewLayout.unitTextInputLayout.editText!!.text.toString()

            groceryList.groceryItemList[index] = groceryItem
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog.show()
    }

}