package com.example.exercise_14.registration

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.exercise_14.BaseFragment
import com.example.exercise_14.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.launch
import kotlin.random.Random

class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

    private val viewModel: RegistrationFragmentViewModel by viewModels()
    private lateinit var userRecyclerAdapter: UserRecyclerAdapter


    override fun setUp() {
        userRecyclerAdapter = UserRecyclerAdapter()
        with(binding) {
            userRecycler.layoutManager = GridLayoutManager(context, 2)
            userRecycler.adapter = userRecyclerAdapter
        }

    }

    override fun bindViewActionListeners() {
        binding.btnAddUser.setOnClickListener {
            addNewUserToViewModelList()
        }

        userRecyclerAdapter.setOnItemClickListener {
            removeUserFromViewModelList(it)
        }
    }

    private fun removeUserFromViewModelList(id: Int) {
        viewModel.removeUser(id)
    }

    private fun addNewUserToViewModelList() {
        viewModel.addNewUser(
            name = binding.etName.text.toString(),
            lastname = binding.etLastname.text.toString(),
            age = binding.etAge.text.toString(),
            status = binding.checkbox.isChecked,
            id = Random.nextInt()
        )
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userFlow.collect {
                    userRecyclerAdapter.submitList(it)
                }
            }
        }
    }
}