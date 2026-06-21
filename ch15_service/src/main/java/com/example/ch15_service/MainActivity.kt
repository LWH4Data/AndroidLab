package com.example.ch15_service

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ch15_outer.MyAIDLInterface
import com.example.ch15_service.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var connectionMode = "none"

    //aidl...........
    var aidlService: MyAIDLInterface? = null
    var aidlJob: Job? = null

    // aidl connection
    val aidlConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            aidlService = MyAIDLInterface.Stub.asInterface(service)
            aidlService?.start()
            binding.aidlProgress.max = aidlService?.maxDuration ?: 0
            val backgroundScope = CoroutineScope(Dispatchers.Main + Job())
            aidlJob = backgroundScope.launch {
                while (binding.aidlProgress.progress < binding.aidlProgress.max) {
                    delay(1000)
                    binding.aidlProgress.incrementProgressBy(1000)
                }
            }
            connectionMode = "aidl"
            changeViewEnable()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            aidlService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //aidl................
        binding.aidlPlay.setOnClickListener {
            bindAIDLService()
        }
        binding.aidlStop.setOnClickListener {
            unbindAIDLService()
        }

        //jobscheduler......................
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted ->
            if (isGranted.values.all { it }) {
                onCreateJobScheduler()
            } else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.POST_NOTIFICATIONS"
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                onCreateJobScheduler()
            } else {
                permissionLauncher.launch(
                    arrayOf("android.permission.POST_NOTIFICATIONS")
                )
            }
        } else {
            onCreateJobScheduler()
        }
    }

    override fun onStop() {
        super.onStop()
        if (connectionMode == "aidl") {
            unbindAIDLService()
        }
    }

    fun changeViewEnable() = when (connectionMode) {
        "aidl" -> {
            binding.aidlPlay.isEnabled = false
            binding.aidlStop.isEnabled = true
        }

        else -> {
            binding.aidlPlay.isEnabled = true
            binding.aidlStop.isEnabled = false
            binding.aidlProgress.progress = 0
        }
    }

    private fun bindAIDLService() {
        val intent = Intent("ACTION_AIDL_SERVICE")
        intent.setPackage("com.example.ch15_outer")
        bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindAIDLService() {
        if (connectionMode == "aidl") {
            aidlService?.stop()
            unbindService(aidlConnection)
            aidlJob?.cancel()
            connectionMode = "none"
            changeViewEnable()
        }
    }

    private fun onCreateJobScheduler() {
        // JobScheduler 로직 구현 부분

        var jobScheduler: JobScheduler? = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val builder = JobInfo.Builder(1, ComponentName(this, MyJobService::class.java))
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
        val jobInfo = builder.build()
        jobScheduler!!.schedule(jobInfo)
    }
}
