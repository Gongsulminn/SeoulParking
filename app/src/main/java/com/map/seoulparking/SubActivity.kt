package com.map.seoulparking

import android.support.v7.app.AppCompatActivity


class SubActivity : AppCompatActivity() {

//    var modelList : List<Model> = ArrayList()
//    lateinit var mAdpater : com.map.seoulparking.Adapter
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sub)
//
//        val parkRetrofit : ParkRetrofit = RetroApiProvider.provideParkApi()
//        val call : retrofit2.Call<List<Model>> = parkRetrofit.parkAllData
//        btn.setOnClickListener {
//            progress.visibility = View.VISIBLE
//            btn.isEnabled = false;
//            call.clone().enqueue(object:Callback<List<Model>>{
//                override fun onResponse(call: Call<List<Model>>?, response: Response<List<Model>>?) {
//                    Log.e("TEST" ,"onResponse" + response.toString())
//                    modelList = response?.body()!!
//                    setRecyclerview()
//                    progress.visibility = View.GONE
//                    btn.isEnabled = true
//                }
//                override fun onFailure(call: Call<List<Model>>?, t: Throwable?) {
//                    Log.e("TEST" ,"ERROR" + t.toString())
//                    Log.e("TEST" ,"call" + call.toString())
//                    progress.visibility = View.GONE
//                    btn.isEnabled =true
//                }
//            })}
//    }
//
//    fun setRecyclerview() : Unit{
//        main_recyclerview.layoutManager =  LinearLayoutManager(this@SubActivity)
//        main_recyclerview.setHasFixedSize(true)
//        mAdpater = Adapter( modelList , this@SubActivity)
//        main_recyclerview.adapter = mAdpater
//    }

}
