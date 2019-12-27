package com.example.belajarmvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.belajarmvvm.R
import com.example.belajarmvvm.data.db.entities.User
import com.example.belajarmvvm.databinding.ActivityLoginBinding
import com.example.belajarmvvm.ui.home.HomeActivity
import com.example.belajarmvvm.util.hide
import com.example.belajarmvvm.util.show
import com.example.belajarmvvm.util.snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       /* val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)
        */

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewModel = viewModel
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
       pb_login.show()
    }

    override fun onSuccess(user: User) {
        pb_login.hide()
        //root_layout_login.snackbar("${user.name} is Logged In")
       // toast("${user.name} is Logged In")
        /*loginResponse.observe(this, Observer {
            toast(it)
        })*/
    }

    override fun onFailure(message: String) {
        pb_login.hide()
        //toast(message)
        root_layout_login.snackbar(message)
    }

    override fun pindahActivity(id: Int) {
        Intent(this, SignupActivity::class.java).also {
            startActivity(it)
        }
    }
}
