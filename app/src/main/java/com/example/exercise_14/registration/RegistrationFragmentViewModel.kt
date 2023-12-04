package com.example.exercise_14.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationFragmentViewModel : ViewModel() {

    private val _usersFlow = MutableStateFlow<List<User>>(emptyList())
    val userFlow: SharedFlow<List<User>> get() = _usersFlow.asStateFlow()

    fun addNewUser(
        name: String, lastname: String, age: String, status: Boolean, id: Int
    ) {
        viewModelScope.launch {
            _usersFlow.value = _usersFlow.value.toMutableList().also {
                it.add(
                    User(
                        name = name,
                        lastname = lastname,
                        age = age,
                        status = status,
                        id = id
                    )
                )
            }
        }
    }

    fun removeUser(id: Int) {
        viewModelScope.launch {
            _usersFlow.value = _usersFlow.value.toMutableList().also{ it ->
                it.removeIf {
                    it.id == id
                }
            }
        }
    }
}