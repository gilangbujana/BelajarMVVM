package com.example.belajarmvvm.data.repositories
import com.example.belajarmvvm.data.db.AppDatabase
import com.example.belajarmvvm.data.db.entities.User
import com.example.belajarmvvm.data.network.MyApi
import com.example.belajarmvvm.data.network.SafeApiRequest
import com.example.belajarmvvm.data.network.responses.AuthResponse

class UserRepository(
    private val api : MyApi,
    private val db : AppDatabase
) : SafeApiRequest(){

    suspend fun userSignup(name: String, email: String, password: String) : AuthResponse {
        return apiRequest{ api.userSignup(name, email, password)}
    }

    suspend fun userLogin(email: String, password: String) : AuthResponse {

        return apiRequest{ api.userLogin(email, password) }
        //val loginResponse = MutableLiveData<String>()

       /* MyApi().userLogin(email, password)
            .enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                   loginResponse.value = t.message
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        loginResponse.value = response.body()?.string()
                    }else{
                        loginResponse.value = response.errorBody()?.string()
                    }
                }

            })

        return loginResponse */
    }

    suspend fun  saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}