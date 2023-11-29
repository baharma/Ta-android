package com.example.ta_android.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ta_android.api.ApiClient
import com.example.ta_android.data.ErrorResponse
import com.example.ta_android.preferences.Invoice
import com.example.ta_android.preferences.UserPreference
import com.example.ta_android.data.UserResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class AuthLoginViewModel(private val pref: UserPreference):ViewModel() {
    private val _authModel = MutableLiveData<Invoice<String>>()
    val authModel: LiveData<Invoice<String>> = _authModel

    fun login(email: String, password: String) {
        _authModel.postValue(Invoice.Loading())
        val client = ApiClient.getApiService().loginUser(email, password)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val loginResult = response.body()?.access_token

                    loginResult?.let { saveUserAuth(it) }

                    _authModel.postValue(Invoice.Success(loginResult))
                } else {
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        ErrorResponse::class.java
                    )
                    _authModel.postValue(Invoice.Error(errorResponse?.message ?: "Unknown error"))
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(
                    AuthLoginViewModel::class.java.simpleName,
                    "onFailure login",
                    t
                )
                _authModel.postValue(Invoice.Error(t.message ?: "Network call failed"))
            }
        })
    }
    fun saveUserAuth(user: String) {
        viewModelScope.launch {
            pref.saveUserAuth(user)
        }
    }

}