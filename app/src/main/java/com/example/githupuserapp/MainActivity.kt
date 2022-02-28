package com.example.githupuserapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var rvUser: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvUser = findViewById(R.id.rvUser)

        val data = mutableListOf<User>(
            User("Adi", "Hai", "Yesterday", R.drawable.user1),
            User("Samsudin", "Hai", "Yesterday", R.drawable.user2),
            User("Suaib", "Hai", "Yesterday", R.drawable.user3),
            User("Sulaiman", "Hai", "Yesterday", R.drawable.user4),
            User("Sukimin", "Hai", "Yesterday", R.drawable.user5),
        )
        val adapter = UserAdapter(data) { myData ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("user", myData)
                startActivity(this)
            }
        }

        rvUser?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        // linearLayout Manager
        // gridLayout Manager
        // staggredGrid Layout manager
        rvUser?.adapter = adapter
    }
}

// Recyclerview
// Adapter
// ItemView -> design item list
// ViewHolder -> class yang melakukan init view yg ada di itemview
// Adapter
// Layout Manager