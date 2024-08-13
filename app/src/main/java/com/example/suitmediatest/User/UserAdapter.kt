package com.example.suitmediatest.User

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmediatest.R

class UserAdapter(
    private val users: List<User>,
    private val onClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.userName)
        val email: TextView = view.findViewById(R.id.userEmail)
        val avatar: ImageView = view.findViewById(R.id.userAvatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.name.text = "${user.first_name} ${user.last_name}"
        holder.email.text = user.email
        Glide.with(holder.avatar.context).load(user.avatar).into(holder.avatar)

        holder.itemView.setOnClickListener {
            onClick(user)
        }
    }

    override fun getItemCount(): Int = users.size
}
