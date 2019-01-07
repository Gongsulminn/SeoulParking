package com.map.seoulparking.dialog

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.map.seoulparking.R
import com.map.seoulparking.databinding.DialogMainBinding
import com.map.seoulparking.model.ParkModel

/**
 * Created by user on 2018-12-26.
 */
class DetailDialog() : DialogFragment() {
    val PARKMODEL: String = "PARKMODEL"
    lateinit var binding: DialogMainBinding
    lateinit var detailModel: ParkModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        detailModel = arguments!!.getParcelable(PARKMODEL)
        binding = DataBindingUtil.inflate(inflater , R.layout.dialog_main , container , false)
        binding.parkmodel = detailModel
        return binding.root
    }

}