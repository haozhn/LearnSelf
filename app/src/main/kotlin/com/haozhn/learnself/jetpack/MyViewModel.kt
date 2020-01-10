package com.haozhn.learnself.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            loadUsers()
        }
    }

    fun getUsers(): MutableLiveData<User> {
        return user
    }

    private fun loadUsers(): User {
        return User("name1", 19)
    }
}