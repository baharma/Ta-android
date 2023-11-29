package com.example.ta_android.menu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.ta_android.MainActivity
import com.example.ta_android.R
import com.example.ta_android.adapter.AdapterViewPages
import com.example.ta_android.databinding.ActivityDasboardBinding
import com.example.ta_android.databinding.ActivityMainBinding
import com.example.ta_android.fragmantDasboard.EventFragment
import com.example.ta_android.fragmantDasboard.HomeFragment
import com.example.ta_android.fragmantDasboard.SertifFragment
import com.example.ta_android.preferences.UserPreference
import com.example.ta_android.viewModel.AuthLoginViewModel
import com.example.ta_android.viewModel.EventListViewModel
import com.example.ta_android.viewModel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class DasboardActivity : AppCompatActivity() {

    private var _binding: ActivityDasboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var adapter: AdapterViewPages
    private  lateinit var eventListViewModel :EventListViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDasboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = UserPreference.getInstance(dataStore)
        eventListViewModel = ViewModelProvider(this, ViewModelFactory(pref,this))[EventListViewModel::class.java]
        eventListViewModel.getToken().observe(
            this
        ){token : String->
            if (token.isEmpty()) {
                val session = Intent(this, MainActivity::class.java)
                startActivity(session)
                finish()
            }else{
                val eventFragment = EventFragment.newInstance(token)
                val homeFragment = HomeFragment.newInstance(token)
                val sertifFragment = SertifFragment.newInstance(token)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.event_fragment, eventFragment)
                    .replace(R.id.home_fragment, homeFragment)
                    .replace(R.id.sertif_fragment, sertifFragment)
                    .commit()
            }
        }
        viewPager = binding.viewPager2
        bottomNav = binding.bottomNav

        adapter = AdapterViewPages(this)
        viewPager.adapter = adapter

        bottomNav.setOnNavigationItemSelectedListener{menuItem ->
            when (menuItem.itemId) {
                R.id.idHome -> {
                    // Tindakan saat item HOME dipilih
                    viewPager.currentItem = 0 // Misalnya, navigasi ke halaman pertama di ViewPager2
                }
                R.id.idEvent -> {
                    // Tindakan saat item DASHBOARDS dipilih
                    viewPager.currentItem = 1 // Misalnya, navigasi ke halaman kedua di ViewPager2
                }
                R.id.idSertiv -> {
                    // Tindakan saat item SERTIF dipilih
                    viewPager.currentItem = 2 // Misalnya, navigasi ke halaman ketiga di ViewPager2
                }
            }
            true
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int){
                super.onPageSelected(position)

                when(position){
                    0 -> bottomNav.selectedItemId = R.id.idHome
                    1 -> bottomNav.selectedItemId = R.id.idEvent
                    2 -> bottomNav.selectedItemId = R.id.idSertiv
                }
            }
        })
    }
}