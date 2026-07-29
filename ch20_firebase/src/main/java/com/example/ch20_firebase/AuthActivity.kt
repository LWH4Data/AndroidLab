package com.example.ch20_firebase

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ch20_firebase.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if(MyApplication.checkAuth()){
            changeVisibility("login")
        }else {
            changeVisibility("logout")
        }

        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider
                    .getCredential(account.idToken, null)
                MyApplication.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            MyApplication.email = account.email
                            // 구글 로그인 성공 시 메인 화면으로 돌아가기 위해 finish() 호출
                            finish()
                        } else {
                            Toast.makeText(baseContext, "구글 로그인 실패: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            changeVisibility("logout")
                        }
                    }
            } catch (e: ApiException) {
                Toast.makeText(baseContext, "구글 로그인 에러: ${e.message}", Toast.LENGTH_SHORT).show()
                changeVisibility("logout")
            }
        }

        binding.logoutBtn.setOnClickListener {
            MyApplication.auth.signOut()
            MyApplication.email = null
            changeVisibility("logout")
        }

        binding.goSignInBtn.setOnClickListener{
            changeVisibility("signin")
        }

        binding.googleLoginBtn.setOnClickListener {
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)
        }

        binding.signBtn.setOnClickListener {
            val email: String = binding.authEmailEditView.text.toString()
            val password: String = binding.authPasswordEditView.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(baseContext, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            MyApplication.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        MyApplication.auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { sendTask ->
                                binding.authEmailEditView.text.clear()
                                binding.authPasswordEditView.text.clear()
                                if (sendTask.isSuccessful) {
                                    Toast.makeText(baseContext, "회원가입 성공. 메일을 확인해주세요.", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")
                                } else {
                                    Toast.makeText(baseContext, "인증 메일 전송 실패: ${sendTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")
                                }
                            }
                    } else {
                        Toast.makeText(baseContext, "회원가입 실패: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        changeVisibility("logout")
                    }
                }
        }

        binding.loginBtn.setOnClickListener {
            val email: String = binding.authEmailEditView.text.toString()
            val password: String = binding.authPasswordEditView.text.toString()
            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        if (MyApplication.checkAuth()) {
                            MyApplication.email = email
                            binding.authEmailEditView.text.clear()
                            binding.authPasswordEditView.text.clear()
                            // 이메일 로그인 성공 시 메인 화면으로 돌아가기 위해 finish() 호출
                            finish()
                        } else {
                            Toast.makeText(baseContext, "이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(baseContext, "로그인 실패: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun changeVisibility(mode: String){
        binding.run {
            when (mode) {
                "login" -> {
                    authMainTextView.text = "${MyApplication.email} 님 반갑습니다."
                    logoutBtn.visibility = View.VISIBLE
                    goSignInBtn.visibility = View.GONE
                    googleLoginBtn.visibility = View.GONE
                    authEmailEditView.visibility = View.GONE
                    authPasswordEditView.visibility = View.GONE
                    signBtn.visibility = View.GONE
                    loginBtn.visibility = View.GONE
                }
                "logout" -> {
                    authMainTextView.text = "로그인 하거나 회원가입 해주세요."
                    logoutBtn.visibility = View.GONE
                    goSignInBtn.visibility = View.VISIBLE
                    googleLoginBtn.visibility = View.VISIBLE
                    authEmailEditView.visibility = View.VISIBLE
                    authPasswordEditView.visibility = View.VISIBLE
                    signBtn.visibility = View.GONE
                    loginBtn.visibility = View.VISIBLE
                }
                "signin" -> {
                    logoutBtn.visibility = View.GONE
                    goSignInBtn.visibility = View.GONE
                    googleLoginBtn.visibility = View.GONE
                    authEmailEditView.visibility = View.VISIBLE
                    authPasswordEditView.visibility = View.VISIBLE
                    signBtn.visibility = View.VISIBLE
                    loginBtn.visibility = View.GONE
                }
            }
        }
    }
}
