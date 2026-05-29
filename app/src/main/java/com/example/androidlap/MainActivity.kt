package com.example.androidlap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// < XML로 작성한 파일을 활용하여 화면 구현 >===================================================
class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 화면 출력 XML 명시.
        setContentView(R.layout.activity_main2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main2)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
        }
    }
}

//// < FrameLayout 활용 >=========================================================================
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = LayoutFrameBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.button.setOnClickListener {
//            binding.button.visibility = View.INVISIBLE
//            binding.imageView.visibility = View.VISIBLE
//        }
//        binding.imageView.setOnClickListener {
//            binding.button.visibility = View.VISIBLE
//            binding.imageView.visibility = View.INVISIBLE
//        }
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_frame)) { v, insets ->
//        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//        insets
//        }
//    }
//}

//// < 바인딩 객체 활용 예 >=========================================================================
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // 바인딩 객체 획득. (생성된 바인딩 객체명을 활용한다).
//        val binding = ViewPracBindingBinding.inflate(layoutInflater)
//        // 액티비티 화면 출력.
//        setContentView(binding.root)
//
//        // 뷰 객체 이용.
//        binding.visibleBtn.setOnClickListener {
//            binding.targetView.visibility = View.VISIBLE
//        }
//    }
//}

// < 액티비티에 화면 구성 >====================================================================
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // 이름 문자열 출력 TextView 생성.
//        // < apply 함수 호출 >
//        //   - 호출하려는 함수가 고차 함수이고 마지막 인자가 람다 함수인 경우 소괄호를
//        //     생략할 수 있다.
//        //     → apply 함수에는 { } 람다식만 전달되기에 ()를 생략하였다.
//        val name = TextView(this).apply {
//            typeface = Typeface.DEFAULT_BOLD
//            text = "Lake Louise"
//        }
//
//        // 이미지 출력 ImageView 생성.
//        val image = ImageView(this).also {
//            it.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.lake_1))
//        }
//
//        // 주소 문자열 출력 TextView 생성.
//        val address = TextView(this).apply {
//            typeface = Typeface.DEFAULT_BOLD
//            text = "Lake Louise, AB, 캐나다"
//        }
//
//        val layout = LinearLayout(this).apply {
//            orientation = LinearLayout.VERTICAL
//            gravity = Gravity.CENTER
//
//            // LinearLayout 객체에 TextView, ImageView, TextView 객체 추가.
//            addView(name, WRAP_CONTENT, WRAP_CONTENT)
//            addView(image, WRAP_CONTENT, WRAP_CONTENT)
//            addView(address, WRAP_CONTENT, WRAP_CONTENT)
//        }
//
//        // LinearLayout 객체를 화면에 출력.
//        setContentView(layout)
//    }
//}


// < 기본 세팅 >=================================================================================
//package com.example.androidlap
//
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//// Activity → AppCompatActivity → MainActivity 순으로 상속된다. 따라서 Activity 클래스이다.
//class MainActivity : AppCompatActivity() {
//    // 앱이 실행되면 onCreate() 함수가 자동으로 호출된다.
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)

//        // enableEdgeToEdge(): 액티비티 화면이 상단의 배터리 표시가 있는 영역(Status Bar)과 하단의
//        //                     안드로이드 버튼이 있는 영역(Navigation Bar)까지 나오게 하는 설정이다.
//        enableEdgeToEdge()

//        // 매개변수에 지정한 내용을 액티비티 화면에 출력한다.
//        //   - 현재는 "res/layout/activity_main.xml"에 구성된 내용이 출력된다.
//        setContentView(R.layout.activity_main)

//        // setOnApplyWindowInsetsListener(): 액티비티에 출력되는 내용이 Navigation Bar 등과 겹치지
//        // 않게 설정한다.
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}