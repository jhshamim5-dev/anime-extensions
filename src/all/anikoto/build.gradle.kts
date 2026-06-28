plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = 34
    namespace = "eu.kanade.tachiyomi.animeextension.all.anikoto"
    defaultConfig { minSdk = 21 }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("io.reactivex.rxjava3:rxjava:3.1.8")
}
