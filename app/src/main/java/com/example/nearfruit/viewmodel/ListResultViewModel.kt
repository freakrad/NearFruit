package com.example.nearfruit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nearfruit.data.model.ModelDetail
import com.example.nearfruit.data.model.ModelResults
import com.example.nearfruit.data.response.ModelResultDetail
import com.example.nearfruit.data.response.ModelResultNearby
import com.example.nearfruit.networking.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListResultViewModel : ViewModel() {

    private val modelResultsMutableLiveData = MutableLiveData<ArrayList<ModelResults>>()
    private val modelDetailMutableLiveData = MutableLiveData<ModelDetail>()

    fun setTokoLocation(strLocation: String) {
        val apiService = ApiClient.getClient()
        val call = apiService.getDataResult(strApiKey, "Toko Buah", strLocation, "distance")
        call.enqueue(object : Callback<ModelResultNearby> {
            override fun onResponse(
                call: Call<ModelResultNearby>,
                response: Response<ModelResultNearby>,
            ) {
                if (!response.isSuccessful) {
                    Log.d("response", response.toString())
                } else if (response.body() != null) {
                    val items = ArrayList(response.body()!!.modelResults)
                    modelResultsMutableLiveData.postValue(items)
                }
            }

            override fun onFailure(call: Call<ModelResultNearby>, t: Throwable) {
                Log.d("failure", t.toString())
            }
        })
    }

    fun setDetailLocation(strPlaceID: String) {
        val apiService = ApiClient.getClient()
        val call = apiService.getDetailResult(strApiKey, strPlaceID)
        call.enqueue(object : Callback<ModelResultDetail> {
            override fun onResponse(
                call: Call<ModelResultDetail>,
                response: Response<ModelResultDetail>,
            ) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    modelDetailMutableLiveData.postValue(response.body()?.modelDetail)
                }
            }

            override fun onFailure(call: Call<ModelResultDetail>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }

    fun getTokoLocation(): LiveData<ArrayList<ModelResults>> = modelResultsMutableLiveData

    fun getDetailLocation(): LiveData<ModelDetail> = modelDetailMutableLiveData

    companion object {
        val strApiKey = "AIzaSyDDjTA424yiGrhrGfro0GlhRACC1SzKIo8"
    }
}