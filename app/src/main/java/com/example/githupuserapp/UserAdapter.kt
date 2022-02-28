package com.example.githupuserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val data: MutableList<User>,
    private val listener: (User) -> Unit
) : RecyclerView.Adapter<UserViewHolder>() {

    // create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)
        )

    // init data ke item view
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(data[position])
        holder.itemView.setOnClickListener {
            listener(data[position])
        }
    }

    // method adalah untuk set berapa item yg mau kita tampilkan
    override fun getItemCount(): Int = data.size
}