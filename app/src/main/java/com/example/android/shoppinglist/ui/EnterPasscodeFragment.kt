package com.example.android.shoppinglist.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.shoppinglist.R
import com.example.android.shoppinglist.databinding.EnterPasscodeFragmentBinding
import com.example.android.shoppinglist.helpers.Utils
import com.example.android.shoppinglist.helpers.hideKeyboard
import com.example.android.shoppinglist.models.User
import kotlinx.android.synthetic.main.create_passcode_fragment.*
import kotlinx.android.synthetic.main.passcode_layout.view.*

class EnterPasscodeFragment : Fragment() {

    companion object {
        fun newInstance() = EnterPasscodeFragment()
    }

    private lateinit var viewModel: EnterPasscodeViewModel
    private lateinit var binding: EnterPasscodeFragmentBinding
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.enter_passcode_fragment, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(EnterPasscodeViewModel::class.java)
        user = EnterPasscodeFragmentArgs.fromBundle(requireArguments()).user
        binding.user = user
        setUpPasscodeLayout()

        binding.loginButton.setOnClickListener {

            if (!getPasscode().isNullOrEmpty() && getPasscode().length ==4){
                if (getPasscode()==user.passcode){
                    Utils.isAuthenticated = true
                    this.findNavController().navigate(EnterPasscodeFragmentDirections.actionEnterPasscodeFragmentToGroceryListsFragment())
                }else{
                    binding.include.passcodeLayout.error = "Invalid passcode"
                }
            }else{
                binding.include.passcodeLayout.error = "Please enter and 4 digit number"
            }
        }
        return binding.root

    }

    fun setUpPasscodeLayout(){
        binding.include.digitOne.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! > 0 && binding.include.digitOne.hasFocus()){
                    binding.include.digitTwo.requestFocus()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.include.digitOne.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(p0: View?, hasFocus: Boolean) {
                if (hasFocus){
                    clearAll()
                    passcodeLayout.isErrorEnabled = false
                }
            }

        })

        binding.include.digitTwo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! > 0 && binding.include.digitTwo.hasFocus()){
                    binding.include.digitThree.requestFocus()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.include.digitThree.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! > 0 && binding.include.digitThree.hasFocus()){
                    binding.include.digitFour.requestFocus()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.include.digitFour.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! > 0 && binding.include.digitFour.hasFocus()){

                    hideKeyboard()
                    //binding.include.digitFour.clearFocus()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun getPasscode():String{
        return binding.include.digitOne.text.toString()+binding.include.digitTwo.text.toString()+binding.include.digitThree.text.toString()+binding.include.digitFour.text.toString()
    }

    private fun clearAll() {
        binding.include.digitOne.setText("")
        binding.include.digitTwo.setText("")
        binding.include.digitThree.setText("")
        binding.include.digitFour.setText("")
    }


}