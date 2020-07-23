package com.example.android.shoppinglist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.shoppinglist.R
import com.example.android.shoppinglist.adapters.GroceryListAdapter
import com.example.android.shoppinglist.database.AppDatabase
import com.example.android.shoppinglist.database.asUserModel
import com.example.android.shoppinglist.databinding.GroceryListsFragmentBinding
import com.example.android.shoppinglist.helpers.Utils
import com.example.android.shoppinglist.helpers.hideKeyboard
import com.example.android.shoppinglist.models.GroceryItem
import com.example.android.shoppinglist.models.GroceryList
import com.example.android.shoppinglist.repository.ShoppingListRepository
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.new_list_bottom_sheet.view.*

class GroceryListsFragment : Fragment(), GroceryListAdapter.GroceryListClickListener {

    companion object {
        fun newInstance() = GroceryListsFragment()
    }

    private lateinit var viewModel: GroceryListsViewModel
    private lateinit var binding: GroceryListsFragmentBinding
    private lateinit var  dialog: BottomSheetDialog
    private lateinit var groceryList: GroceryList
    private lateinit var adapter: GroceryListAdapter
    private lateinit var alertDialog: AlertDialog
    private var dialogShown= false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.grocery_lists_fragment, container, false)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val  database = AppDatabase.getDatabse(application)

        val shoppingListRepository =  ShoppingListRepository(database!!)
        val viewModelFactory = ViewModelFactory(shoppingListRepository)
        viewModel  = ViewModelProvider(this,viewModelFactory).get(GroceryListsViewModel::class.java)

        adapter = GroceryListAdapter(this,requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.appUser.observe(viewLifecycleOwner, Observer {
            if(!Utils.isAuthenticated) {
                if (it != null) {
                    this.findNavController().navigate(
                        GroceryListsFragmentDirections.actionGroceryListsFragmentToEnterPasscodeFragment(
                            it.asUserModel()
                        )
                    )
                } else {
                    this.findNavController()
                        .navigate(GroceryListsFragmentDirections.actionGroceryListsFragmentToCreatePasscodeFragment())
                }
            }
        })

        viewModel.groceryList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                binding.noItems.visibility = View.GONE
            }
            it.let {
                adapter.submitList(it)
            }
        })
        binding.addGroceryListActionButton.setOnClickListener {
            val viewLayout = layoutInflater.inflate(R.layout.new_list_bottom_sheet,null)
            dialog = BottomSheetDialog(requireActivity())
            dialog.setContentView(viewLayout)
            viewLayout.createListNameButton.setOnClickListener {
                val listName = viewLayout.listNameTextInputLayout.editText!!.text.toString()
                if(listName.isNullOrEmpty()){
                    viewLayout.listNameTextInputLayout.error = "Please enter a list name"
                }else{
                    val groceryItemList =ArrayList<GroceryItem>()
                     groceryList = GroceryList(listName,groceryItemList)
                    viewModel.addGroceryListToDB(groceryList)
                    dialogShown = false
                }
            }
            viewLayout.listNameTextInputLayout.editText!!.setOnEditorActionListener { view, actionId, keyEvent ->
                when(actionId){
                    EditorInfo.IME_ACTION_DONE->{
                        val listName = viewLayout.listNameTextInputLayout.editText!!.text.toString()
                        if(!listName.isNullOrEmpty()){
                            hideKeyboard()
                            val groceryItemList =ArrayList<GroceryItem>()
                            groceryList = GroceryList(listName,groceryItemList)
                            viewModel.addGroceryListToDB(groceryList)
                            dialogShown = false
                        }
                        true
                    }
                    else -> false
                }
            }
            dialog.show()
        }
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it == ShoppingListStatus.DONE){
                dialog.dismiss()
                showDialog()
            }
        })
        return binding.root
    }

    fun showDialog(){
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("List created")
        dialogBuilder.setMessage("A new list has been created do you want to add items now")
        dialogBuilder.setPositiveButton("Yes") { dialogInterface, which ->
            dialogInterface.cancel()
            onClick(groceryList)
        }
        dialogBuilder.setNegativeButton("Later"){dialogInterface, i ->
            dialogInterface.dismiss()

        }
         alertDialog = dialogBuilder.create()
        if(!dialogShown) {
            dialogShown = true
            alertDialog.show()
        }

    }

    override fun onClick(groceryList: GroceryList) {
        super.onClick(groceryList)
        this.findNavController().navigate(GroceryListsFragmentDirections.actionGroceryListsFragmentToGroceryDetailsFragment(groceryList))
    }

    override fun onDelete(groceryList: GroceryList) {
        super.onDelete(groceryList)
        viewModel.deleteGroceryList(groceryList)
    }

}