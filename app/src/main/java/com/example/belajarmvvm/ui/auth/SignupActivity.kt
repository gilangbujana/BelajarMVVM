package com.example.belajarmvvm.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.belajarmvvm.R
import com.example.belajarmvvm.data.db.entities.User
import com.example.belajarmvvm.databinding.ActivitySignupBinding
import com.example.belajarmvvm.ui.home.HomeActivity
import com.example.belajarmvvm.util.hide
import com.example.belajarmvvm.util.show
import com.example.belajarmvvm.util.snackbar
import kotlinx.android.synthetic.main.activity_signup.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()
    val factory : AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if(user != null){
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        pb_signup.show()
    }

    override fun onSuccess(user: User) {
        pb_signup.hide()
    }

    override fun onFailure(message: String) {
        pb_signup.hide()
        root_layout_signup.snackbar(message)
    }

    override fun pindahActivity(id: Int) {
        finish()
    }
}
