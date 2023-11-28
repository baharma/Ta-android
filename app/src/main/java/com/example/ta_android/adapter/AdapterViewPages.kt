package com.example.ta_android.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ta_android.fragmantDasboard.EventFragment
import com.example.ta_android.fragmantDasboard.HomeFragment
import com.example.ta_android.fragmantDasboard.SertifFragment

class AdapterViewPages(activity: AppCompatActivity): FragmentStateAdapter(activity) {

 override fun createFragment(position: Int): Fragment {
   var fragment : Fragment? = null
  when (position){
   0->fragment = HomeFragment()
   1->fragment = EventFragment()
   2->fragment = SertifFragment()
  }
  return fragment as Fragment
 }

 override fun getItemCount(): Int {
  return 3
 }


}