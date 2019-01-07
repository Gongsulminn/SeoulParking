package com.map.seoulparking

import android.Manifest
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.map.seoulparking.databinding.ActivitySplashBinding
import com.map.seoulparking.model.ParkModel
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
        RetroApiProvider.provideParkApi().getParkUsedData().clone().enqueue(object: Callback<List<ParkModel>>{
            override fun onResponse(call: Call<List<ParkModel>>?, response: Response<List<ParkModel>>?) {
                // var model: List<ParkModel> = response!!.body()

                var model : ArrayList<ParkModel>  = response!!.body() as ArrayList<ParkModel>

                val permissionlistener = object : PermissionListener {
                    override fun onPermissionGranted() {
                        var intent: Intent = Intent(this@SplashActivity , MainActivity::class.java)
                        intent.putParcelableArrayListExtra("data" , model)
                        intent.putExtra("locationBoolean",true)
                        startActivity(intent)
                        finish()
                    }

                    override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>) {
                        Log.d("디버그", "onPermissionDenied : " + deniedPermissions.toString())
                        var intent: Intent = Intent(this@SplashActivity , MainActivity::class.java)
                        intent.putParcelableArrayListExtra("data" , model)
                        intent.putExtra("locationBoolean",false)
                        startActivity(intent)
                        finish()
                    }
                }

                TedPermission.with(applicationContext)
                        .setPermissionListener(permissionlistener)
                        .setPermissions(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                        .check()

                Log.e(TAG , response?.body().toString())
            }

            override fun onFailure(call: Call<List<ParkModel>>?, t: Throwable?) {
                Log.e(TAG , t.toString())
            }

        })

    }
}

