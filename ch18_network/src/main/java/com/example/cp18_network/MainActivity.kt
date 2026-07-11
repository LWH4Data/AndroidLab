package com.example.ch18_network

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch18_network.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var volleyFragment: VolleyFragment
    lateinit var retrofitFragment: RetrofitFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar를 Action Bar로 설정
        setSupportActionBar(binding.toolbar)

        volleyFragment = VolleyFragment()
        retrofitFragment = RetrofitFragment()

        // 초기 화면 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_content, volleyFragment)
                .commit()
            supportActionBar?.title = "Volley Test"
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_volley -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, volleyFragment)
                    .commit()
                supportActionBar?.title = "Volley Test"
            }
            R.id.menu_retrofit -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, retrofitFragment)
                    .commit()
                supportActionBar?.title = "Retrofit Test"
            }
        }
        return super.onOptionsItemSelected(item)
    }
}