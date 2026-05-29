// 플러그인을 선언한다.
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.androidlap"
    // 앱을 컴파일하거나 빌드할 때 적용할 버전을 설정한다.
    compileSdk {
        // 버전을 명시한다. 현재는 SDK 36 버전을 적용하여 컴파일하라는 의미이다.
        version = release(36) {
            minorApiLevel = 1
        }
    }
    // 뷰 바인딩 설정 추가.
    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        // 앱의 식별자를 설정한다.
        //   - 지정한 문자열은 식별자가 되기에 고유한 문자열로 지정해야 한다.
        //   - 구글 플레이 스토어에 등록된 앱 중 동일한 식별자를 사용하는 앱이 있다면
        //     해당 앱은 플레이 스토어에 등록되지 않는다.
        //     - 마찬가지로 설치 또한 되지 않는다.
        applicationId = "com.xxx.xxx"
        // 앱을 설치할 수 있는 기기의 최소 SDK 버전
        minSdk = 24
        // 개발할 때 적용되는 SDK 버전
        targetSdk = 36
        // 앱의 버전을 설정한다.
        //   - 초깃값은 1이다.
        //   - 앱이 업데이트될 때마다 해당 버전을 올려서 배포한다.
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    // 개발 언어의 버전을 설정한다.
    //   - 생략할 경우 자동적으로 1.6이 적용된다.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// 앱에서 이용하는 라이브러리의 버전을 설정한다.
//   - targetSdk에 명시한 안드로이드 SDK 외에 추가되는 모든 라이브러리를 여기서 추가한다.
//   - 라이브러리 버전은 시기와 책에 따라 다를 수 있다.
dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.runtime.saved.instance.state)
    implementation(libs.material)
    // ConstraintLayout을 위한 의존성 추가.
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}