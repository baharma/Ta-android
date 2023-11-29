package com.example.ta_android.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ta_android.api.ApiClient
import com.example.ta_android.data.EventResponse
import com.example.ta_android.preferences.Invoice
import com.example.ta_android.preferences.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventListViewModel(private val pref: UserPreference) : ViewModel() {
    private val _errorModel = MutableLiveData<Invoice<String>>()
    val errorModel: LiveData<Invoice<String>> = _errorModel

    private val _eventResponse = MutableLiveData<EventResponse>()
    val eventResponse: LiveData<EventResponse> = _eventResponse

     fun getAllData(authToken : String){
        _errorModel.postValue(Invoice.Loading())
        val client = ApiClient.getApiService().getListEvent(authToken)
        client.enqueue(object : Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val error = responseBody.error // Obtain error status from the response
                        val message = responseBody.message // Obtain message from the response
                        val listEvent = responseBody.listEvent // Obtain list of events from the response
                        val eventResponse = EventResponse(error, message, listEvent)
                        _eventResponse.postValue(eventResponse)
                        _errorModel.postValue(Invoice.Success(message))
                    } else {
                        Log.e(TAG, "Response body is null")
                        _errorModel.postValue(Invoice.Error("Response body is null"))
                    }
                } else {
                    Log.e(TAG, "Unsuccessful response: ${response.message()}")
                    _errorModel.postValue(Invoice.Error("Unsuccessful response: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _errorModel.postValue(Invoice.Error(" ${t.message}"))
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    fun getToken(): LiveData<String> {
        return pref.getUserAuth().asLiveData()
    }
}