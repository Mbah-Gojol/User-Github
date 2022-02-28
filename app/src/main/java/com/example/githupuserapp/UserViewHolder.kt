package com.example.githupuserapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
    private val image: ImageView = v.findViewById(R.id.image)
    private val nama: TextView = v.findViewById(R.id.nama)
    private val lastChat: TextView = v.findViewById(R.id.lastchat)
    private val date: TextView = v.findViewById(R.id.date)

    fun onBind(data: User) {
        this.nama.text = data.nama
        this.lastChat.text = data.lastChat
        this.date.text = data.date
        this.image.setImageResource(data.img)
    }
}