import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("org.jlleitschuh.gradle.ktlint")
}

var versionMajor = 1
var versionMinor = 0
var versionBuild = 0

android {
    namespace = "com.canopas.catchme"
    compileSdk = 34

    if (System.getenv("CI_RUN_NUMBER") != null) {
        versionBuild = System.getenv("CI_RUN_NUMBER").toInt()
    } else {
        versionMajor = 1
        versionMinor = 0
        versionBuild = 0
    }

    defaultConfig {
        applicationId = "com.canopas.catchme"
        minSdk = 21
        targetSdk = 34
        versionCode = versionMajor * 1000000 + versionMinor * 10000 + versionBuild
        versionName = "$versionMajor.$versionMinor.$versionBuild"
        setProperty("archivesBaseName", "CatchMe-$versionName-$versionCode")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        if (System.getenv("MAPS_API_KEY") != null) {
            manifestPlaceholders["MAPS_API_KEY"] = System.getenv("MAPS_API_KEY")
        } else {
            val p = Properties()
            p.load(project.rootProject.file("local.properties").reader())
            manifestPlaceholders["MAPS_API_KEY"] = p.getProperty("MAPS_API_KEY")
        }
    }

    signingConfigs {
        if (System.getenv("APKSIGN_KEYSTORE") != null) {
            getByName("debug") {
                keyAlias = System.getenv("APKSIGN_KEY_ALIAS")
                keyPassword = System.getenv("APKSIGN_KEYSTORE_PASS")
                storeFile = file(System.getenv("APKSIGN_KEYSTORE"))
                storePassword = System.getenv("APKSIGN_KEY_PASS")
            }
        } else {
            getByName("debug") {
                keyAlias = "catchme"
                keyPassword = "catchme"
                storeFile = file("debug.keystore")
                storePassword = "catchme"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    ktlint {
        debug = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:4.5.1")
    testImplementation("org.mockito:mockito-core:5.7.0")

    // Hilt
    val hilt = "2.50"
    implementation("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-compiler:$hilt")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // country-picker
    implementation("com.canopas.jetcountrypicker:jetcountrypicker:1.0.9")

    // coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Accompanist permission
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")

    implementation("com.google.maps.android:maps-compose:4.3.0")

    implementation(project(":data"))
}
