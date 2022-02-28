package com.example.githupuserapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val nama: String, val lastChat: String, val date: String, val img: Int) : Parcelable