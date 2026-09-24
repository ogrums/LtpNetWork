package com.letianpai.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.letianpai.network.common.AppUtils
import com.letianpai.network.model.OrderModel
import com.letianpai.network.template.repository.NetDataRepository
import com.letianpai.network.template.repository.NetDataRepositorySource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Log.d("<<<", "mac address:"+AppUtils.getWifiMac2(this))
//        Log.d("<<<", "SN :"+AppUtils.getSerialNo())

        findViewById<Button>(R.id.button).setOnClickListener {
            GlobalScope.launch {
                test4()
            }
        }
    }

    private suspend fun test4(){
        val repo: NetDataRepositorySource = NetDataRepository(this)
        val hashMap = HashMap<String, Any>()
        hashMap["sn"] = AppUtils.getSerialNo()
        hashMap["ts"] = System.currentTimeMillis()/1000
        repo.getIotTriplet(hashMap).collect {
            Log.d("<<<", "result it=${it.data}")
        }
    }

    private suspend fun test3(){
        val repo: NetDataRepositorySource = NetDataRepository(this)
//        repo.getOrderModelReq().collect {
//            Log.d("<<<", "it=${it.data?.addressModel?.cityName}")
//        }

        val hashMap = HashMap<String, Any>()
        hashMap["mac"] = AppUtils.getWifiMac2(this)
        hashMap["ts"] = System.currentTimeMillis()/1000

        repo.getSnHardcode(hashMap).collect {
            Log.d("<<<", "it=${it.data}")
        }
    }

    private suspend fun test2(){
        val repo: NetDataRepositorySource = NetDataRepository(this)
//        val addressModel = AddressModel("province", "city", "district", "")
//        val order = OrderModel(addressModel, 0.0, "123")
//        repo.getCaptchaSmsRequest(order).collect {
//            Log.d("<<<", "it=${it.data}")
//        }
    }

    private suspend fun test(){
        val repo: NetDataRepositorySource = NetDataRepository(this)
        repo.getTestRequest().collect {
            Log.d("<<<", "it=${it.data}")
        }
    }
}