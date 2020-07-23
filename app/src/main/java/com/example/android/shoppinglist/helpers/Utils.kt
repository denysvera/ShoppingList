package com.example.android.shoppinglist.helpers

import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Utils {
    companion object{
        fun isConsecutive(s: String?): Boolean {
            if (s == null || s.length < 2) {
                return false
            }
            var b = true
            try {
                val chars = s.trim { it <= ' ' }.split("").toTypedArray()
                for (i in 2 until chars.size-1) {
                    val int1 = Integer.valueOf(chars[i - 1])
                    val int2 = Integer.valueOf(chars[i])
                    if (int2 != int1 + 1) {
                        b = false
                    }
                }
            } catch (e: NumberFormatException) {
                return false
            }
            return b
        }

        fun isSame(s: String?): Boolean {
            if (s == null || s.length < 2) {
                return false
            }
            var b = true
            val chars = s.trim { it <= ' ' }.split("").toTypedArray()
            for (i in 2 until chars.size-1) {
                if (chars[i] != chars[i - 1]) {
                    b = false
                }
            }
            return b
        }

        var isAuthenticated = false
    }

}
fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
     else {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
     }
}
fun Fragment.hideKeyboard() {
    val activity = this.activity
    if (activity is AppCompatActivity) {
        activity.hideKeyboard()
    }
}