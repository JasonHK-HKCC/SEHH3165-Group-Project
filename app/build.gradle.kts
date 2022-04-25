plugins {
    id("com.android.application")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "app.jasonhk.hkcc.sleeptracker"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            // minifyEnabled false
            // proguardFiles getDefaultProguardFile("proguard-android-optimize.txt"),
            //               "proguard-rules.pro"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.22")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    testCompileOnly("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.22")

    implementation("io.reactivex.rxjava3:rxjava:3.1.4")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")

    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")

    implementation("androidx.preference:preference:1.2.0")

    val roomVersion = "2.4.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-rxjava3:$roomVersion")

    val nav_version = "2.4.1"
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}