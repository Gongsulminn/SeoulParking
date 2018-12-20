package com.map.seoulparking

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.map.seoulparking.databinding.ActivitySplashBinding
import com.map.seoulparking.model.ParkModel1
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by user on 2018-12-18.
 */
class SplashActivity : AppCompatActivity(){
    lateinit var binding : ActivitySplashBinding
    val TAG: String  ="SplashActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SplashActivity , R.layout.activity_splash)


        // 여기를 repo로 만들기
        RetroApiProvider.provideParkApi().getParkUsedData().clone().enqueue(object: Callback<List<ParkModel1>>{

            override fun onResponse(call: Call<List<ParkModel1>>?, response: Response<List<ParkModel1>>?) {
                // var model: List<ParkModel> = response!!.body()

                var model : ArrayList<ParkModel1>  = response!!.body() as ArrayList<ParkModel1>
                var intent: Intent = Intent(this@SplashActivity , MainActivity::class.java)
                intent.putParcelableArrayListExtra("data" , model)
                startActivity(intent)
                Log.e(TAG , response?.body().toString())
            }

            override fun onFailure(call: Call<List<ParkModel1>>?, t: Throwable?) {
                Log.e(TAG , "onFailure")
            }

        })

    }
}

