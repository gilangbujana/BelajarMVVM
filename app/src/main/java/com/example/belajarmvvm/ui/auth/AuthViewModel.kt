package com.example.belajarmvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.belajarmvvm.data.repositories.UserRepository
import com.example.belajarmvvm.util.ApiException
import com.example.belajarmvvm.util.Coroutines
import com.example.belajarmvvm.util.NoInternetException
import org.json.JSONException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel(){

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordConfirm: String? = null

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

   /* fun onSignup(view: View){
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onLogin(view: View){
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }*/

    fun onChangeActivity(view: View){
        authListener?.pindahActivity(view.id)
    }

    fun onSignupButtonClick(view: View){
        authListener?.onStarted()

        if(name.isNullOrEmpty()){
            //onFailure
            authListener?.onFailure("Kolom Nama Harus diisi")
            return
        }

        if(email.isNullOrEmpty()){
            //onFailure
            authListener?.onFailure("Kolom email harus diisi")
            return
        }

        if(password.isNullOrEmpty()){
            //onFailure
            authListener?.onFailure("Kolom password harus diisi")
            return
        }

        if(passwordConfirm.isNullOrEmpty()){
            //onFailure
            authListener?.onFailure("Kolom konfirmasi password harus diisi")
            return
        }



        if(!password.equals(passwordConfirm)){
            authListener?.onFailure("password tidak sama")
            return
        }


        Coroutines.main {

            try{
                val authResponse = repository.userSignup(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.pesan!!)
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun onLoginButtonClick(view: View){

        //onStart
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            //onFailure
            authListener?.onFailure("email sama password gak boleh kosong")
            return
        }

        Coroutines.main {

            try{
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.pesan!!)
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }

            /*

            val response = UserRepository().userLogin(email!!, password!!)
            if(response.isSuccessful){
                authListener?.onSuccess(response.body()?.user!!)
            }else{
                val error = response.errorBody()?.string()
                error?.let {
                    try{

                    }catch (e: JSONException){

                    }
                }
            }*/
        }
        //onSuccess

        //authListener?.onSuccess(loginResponse)
    }
}