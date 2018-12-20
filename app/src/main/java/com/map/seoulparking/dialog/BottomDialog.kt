package com.map.seoulparking.dialog

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.map.seoulparking.R
import com.map.seoulparking.databinding.DialogBottomBinding

class BottomDialog: BottomSheetDialogFragment() {
    lateinit var binding: DialogBottomBinding
    val TAG: String = "BottomDialog"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    companion object {
        @JvmStatic
       open fun newInstance():BottomDialog =
                BottomDialog().apply {

                }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(TAG , "onCreateView")
        return inflater.inflate(R.layout.dialog_bottom , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG , "onViewCreated")
    }
}