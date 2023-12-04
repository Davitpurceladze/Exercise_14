package com.example.exercise_14.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_14.databinding.ItemUserTeacherBinding

class UserRecyclerAdapter : ListAdapter<User, UserRecyclerAdapter.UserViewHolder>(UserDiffUtil()) {

    private var onItemClickListener: ( (id: Int)->Unit)? = null

    fun setOnItemClickListener(listener: (id: Int)->Unit){
        onItemClickListener = listener
    }

    inner class UserViewHolder(private val binding: ItemUserTeacherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var user: User
        fun bind() {
            user = currentList[adapterPosition]
            with(binding) {
                tvUserName.text = user.name
                tvUserLastName.text = user.lastname
                tvUserAge.text = user.age
                tvUserStatus.text = userStatus()
                userDelete.setOnClickListener {
                    onItemClickListener?.invoke(user.id)
                }
            }

        }

        private fun userStatus(): String {
            return if (user.status) {
                "Teacher"
            } else {
                "Student"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserTeacherBinding.inflate(layoutInflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind()
    }

    class UserDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

}