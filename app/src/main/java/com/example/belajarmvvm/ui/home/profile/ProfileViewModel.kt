package com.example.belajarmvvm.ui.home.profile

import androidx.lifecycle.ViewModel
import com.example.belajarmvvm.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()
}
