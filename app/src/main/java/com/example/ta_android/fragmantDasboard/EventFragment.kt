package com.example.ta_android.fragmantDasboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.ta_android.MainActivity
import com.example.ta_android.R
import com.example.ta_android.databinding.FragmentEventBinding
import com.example.ta_android.preferences.UserPreference
import com.example.ta_android.viewModel.EventListViewModel
import com.example.ta_android.viewModel.ViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
class EventFragment : Fragment() {

    private var _binding : FragmentEventBinding? = null
    private val binding get() = _binding!!
    private  lateinit var eventListViewModel :EventListViewModel


    companion object {
        private const val ARG_TOKEN = "arg_token"

        fun newInstance(token: String): EventFragment {
            val fragment = EventFragment()
            val args = Bundle()
            args.putString(ARG_TOKEN, token)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = requireContext().dataStore

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }


}