package com.grud.lojavirtualfirebase.helper

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(){

    //OCUTAR TECLADO QUANDO PRECIONADO O BTN
    fun hideKeyboard(){
        val view = activity?.currentFocus
        if(view != null){
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
}