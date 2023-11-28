package com.example.ta_android.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ta_android.preferences.UserPreference

class ViewModelFactory(private val pref: UserPreference, private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthLoginViewModel::class.java)) {
            return AuthLoginViewModel(pref) as T
        }
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(pref,Injection.provideRepository(context)) as T
//        }
//        if (modelClass.isAssignableFrom(ViewModelAddstory::class.java)) {
//            return ViewModelAddstory(pref) as T
//        }
//        if (modelClass.isAssignableFrom(ViewModelLogout::class.java)) {
//            return ViewModelLogout(pref) as T
//        }
//        if(modelClass.isAssignableFrom(ViewModelMaps::class.java)){
//            return ViewModelMaps(pref) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}