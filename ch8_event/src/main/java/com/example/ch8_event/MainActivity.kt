package com.example.ch8_event

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.ch8_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var initTime = 0L
    var pauseTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            // 기본 시작은 현재 시간 + pauseTime으로 한다.
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            // 시간 시작
            binding.chronometer.start()

            // start 버튼 클릭시 다른 버튼 상태 업데이트.
            binding.stopButton.isEnabled = true
            binding.resetButton.isEnabled = true
            binding.startButton.isEnabled = false
        }

        binding.stopButton.setOnClickListener {
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            binding.stopButton.isEnabled = false
            binding.resetButton.isEnabled = true
            binding.startButton.isEnabled = true
        }

        binding.resetButton.setOnClickListener {
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            binding.stopButton.isEnabled = false
            binding.resetButton.isEnabled = false
            binding.startButton.isEnabled = true
        }

        // OnBackPressedCallback 등록.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true)
        {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - initTime > 3000) {
                    Toast.makeText(this@MainActivity, "종료하려면 한 번 더 누르세요",
                        Toast.LENGTH_SHORT).show()
                    initTime = System.currentTimeMillis()
                } else {
                    // 실제로 종료.
                    finish()
                }
            }
        })
    }
}