plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

android {
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
    namespace = "extensions.core"
    sourceSets {
        named("main") {
            manifest.srcFile("AndroidManifest.xml")
            res.srcDirs("src/main/res")
        }
    }
    buildFeatures {
        resValues = false
        buildConfig = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    compileOnly("com.github.aniyomiorg:extensions-lib:59418e2c2aab286dc654dde31e071aa82cb0ff7c")
    compileOnly("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    compileOnly("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    compileOnly("org.jsoup:jsoup:1.16.1")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    compileOnly("io.reactivex.rxjava3:rxjava:3.1.8")
    compileOnly("uy.kohesive.injekt:injekt-core:1.16.1")
}
