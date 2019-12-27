package com.example.belajarmvvm.ui.auth
import android.view.View
import com.example.belajarmvvm.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
    fun pindahActivity(id: Int)
}