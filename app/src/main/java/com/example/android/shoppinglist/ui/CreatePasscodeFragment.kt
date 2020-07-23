package com.example.android.shoppinglist.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.android.shoppinglist.R
import com.example.android.shoppinglist.database.AppDatabase
import com.example.android.shoppinglist.databinding.CreatePasscodeFragmentBinding
import com.example.android.shoppinglist.helpers.Utils
import com.example.android.shoppinglist.helpers.hideKeyboard
import com.example.android.shoppinglist.repository.ShoppingListRepository
import kotlinx.android.synthetic.main.create_passcode_fragment.*

class CreatePasscodeFragment : Fragment() {

    companion object {
        fun newInstance() = CreatePasscodeFragment()
    }

    private lateinit var viewModel: CreatePasscodeViewModel
    private lateinit var binding: CreatePasscodeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_passcode_fragment, container, false)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val  database = AppDatabase.getDatabse(application)

        val shoppingListRepository =  ShoppingListRepository(database!!)
        val viewModelFactory = ViewModelFactory(shoppingListRepository)
        viewModel  = ViewModelProvider(this,viewModelFactory).get(CreatePasscodeViewModel::class.java)
        setUpPasscodeLayout()

        binding.createButton.setOnClickListener{
            if (validateInput()){
                viewModel.createUser(getUserName(),getPasscode())
            }
        }

        viewModel.appUser.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Utils.isAuthenticated = true
                this.findNavController().navigate(CreatePasscodeFragmentDirections.actionCreatePasscodeFragmentToGroceryListsFragment())
            }
        })
        return binding.root
    }

    fun validateInput(): Boolean{
        var result = true
        if(getUserName().isNullOrEmpty()){
            result = false
            binding.userNameInputLayout.error = "Please enter your username"
        }

        if (getPasscode().isNullOrEmpty() || getPasscode().length !=4){
            result = false
            binding.passcodeLayout.error = "Please enter and 4 digit number"
        }else{
            result = isPasscodeValid()
        }
        return result
    }
    private fun getUserName(): String{
        return binding.userNameInputLayout.editText!!.text.toString()
    }

    fun setUpPasscodeLayout(){
        binding.digitOne.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! > 0 && binding.digitOne.hasFocus()){
                    binding.digitTwo.requestFocus()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.digitOne.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(p0: View?, hasFocus: Boolean) {
                if (hasFocus){
                    clearAll()
                    passcodeLayout.isErrorEnabled = false
                }
            }

        })

        binding.digitTwo.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! > 0 && binding.digitTwo.hasFocus()){
                    binding.digitThree.requestFocus()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.digitThree.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! > 0 && binding.digitThree.hasFocus()){
                    binding.digitFour.requestFocus()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.digitFour.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! > 0 && binding.digitFour.hasFocus()){

                    hideKeyboard()
                    binding.digitFour.clearFocus()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun getPasscode():String{
        return digitOne.text.toString()+binding.digitTwo.text.toString()+binding.digitThree.text.toString()+binding.digitFour.text.toString()
    }

    fun isPasscodeValid(): Boolean{
        return if (Utils.isConsecutive(getPasscode())){
            passcodeLayout.error = getString(R.string.passcode_cannot_have_consecutive)
            false
        }else if (Utils.isSame(getPasscode())){
            passcodeLayout.error = getString(R.string.passcode_cannot_be_same)
            false
        }else{
            true
        }
    }

    private fun clearAll() {
        binding.digitOne.setText("")
        binding.digitTwo.setText("")
        binding.digitThree.setText("")
        binding.digitFour.setText("")
    }

}