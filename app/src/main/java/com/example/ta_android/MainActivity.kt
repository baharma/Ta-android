package com.example.ta_android

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.ta_android.databinding.ActivityMainBinding
import com.example.ta_android.menu.DasboardActivity
import com.example.ta_android.preferences.Invoice
import com.example.ta_android.preferences.UserPreference
import com.example.ta_android.viewModel.AuthLoginViewModel
import com.example.ta_android.viewModel.ViewModelFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
class MainActivity : AppCompatActivity() {
    private lateinit var authModel: AuthLoginViewModel
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var state: Boolean = false // Deklarasi variabel state

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClickLogin()
        setupViewModel() // Panggil setupViewModel dari onCreate
    }

    private fun setupViewModel() {
        val pref = UserPreference.getInstance(dataStore)
        authModel = ViewModelProvider(this, ViewModelFactory(pref, this))[AuthLoginViewModel::class.java]

        authModel.authModel.observe(this) {
            when (it) {
                is Invoice.Success -> {
                    showLoading(false)
                    startActivity(Intent(this, DasboardActivity::class.java))
                    finish()
                    state = true // Atur nilai state di sini
                }
                is Invoice.Loading -> showLoading(true)
                is Invoice.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    private fun onClickLogin() {

        binding.login.setOnClickListener {

            val email = binding.emailId.text.toString()
            val password = binding.passwordId.text.toString()

            when {
                email.isNullOrBlank() -> {
                    binding.email.error = getString(R.string.email_dont_match)
                }
                password.isNullOrBlank() -> {
                    binding.password.error = getString(R.string.password_dont_match)
                }
                email.isNullOrEmpty() -> {
                    binding.email.error = "Masukkan email"
                }
                password.isNullOrEmpty() -> {
                    binding.password.error = "Masukan Password"
                }
                else -> {
                    closeKeyboard(this)
                    authModel.login(email, password)
                }

            }
        }
    }
    private fun showLoading(state: Boolean) {
        binding.progressBarLogin.visibility = if (state) View.VISIBLE else View.GONE
        binding.login.isEnabled = !state
    }




    override fun onStop() {
        super.onStop()
        if (state)
            finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun closeKeyboard(activity: AppCompatActivity) {
        val view: View? = activity.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
