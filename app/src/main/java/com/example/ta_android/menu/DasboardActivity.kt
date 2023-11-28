package com.example.ta_android.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.ta_android.R
import com.example.ta_android.adapter.AdapterViewPages
import com.example.ta_android.databinding.ActivityDasboardBinding
import com.example.ta_android.databinding.ActivityMainBinding
import com.example.ta_android.viewModel.AuthLoginViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class DasboardActivity : AppCompatActivity() {

    private var _binding: ActivityDasboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var adapter: AdapterViewPages
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDasboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

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