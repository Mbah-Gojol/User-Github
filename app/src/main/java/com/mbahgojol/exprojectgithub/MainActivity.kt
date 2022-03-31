package com.mbahgojol.exprojectgithub

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbahgojol.exprojectgithub.data.local.SettingPreferences
import com.mbahgojol.exprojectgithub.data.model.ResponseUserGithub
import com.mbahgojol.exprojectgithub.databinding.ActivityMainBinding
import com.mbahgojol.exprojectgithub.detail.DetailActivity
import com.mbahgojol.exprojectgithub.favorite.FavoriteActivity
import com.mbahgojol.exprojectgithub.setting.SettingActivity
import com.mbahgojol.exprojectgithub.utils.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter { user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }
    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getUser(p0.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean = false

        })

        viewModel.resultUser.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    adapter.setData(it.data as MutableList<ResponseUserGithub.Item>)
                }
                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }

        viewModel.getUser()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                Intent(this, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.setting -> {
                Intent(this, SettingActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

//Entity -> Table
//Dao -> Kumpulan Query
//AppDatabase -> Configurasi dari database room
