/* ktlint-disable no-multi-spaces max-line-length */
object deps {
    object android {
        const val targetSdkVersion  = 33
        const val compileSdkVersion = 33
        const val minSdkVersion     = 24
        const val buildToolsVersion = "30.0.2"
    }

    object versions {
        const val glide           = "4.12.0"
        const val dagger          = "2.19"
        const val gms             = "17.0.0"
        const val kotlin          = "1.6.21"
        const val okHttp          = "4.9.1"
        const val retrofit        = "2.9.0"
        const val work            = "2.7.1"
        const val navigation      = "2.3.5"
        const val lifecycle       = "2.5.1"
        const val leanback        = "1.1.0-rc01"
        const val googleApiClient = "1.32.1"
        const val paging          = "3.1.1"
        const val room            = "2.4.2"
        const val epoxy           = "4.6.3-vinay-compose"
        const val serialization   = "1.2.2"
        const val fragment        = "1.5.1"
        const val activity        = "1.5.1"
        const val libretrodroid   = "0.9.0"
        const val radialgamepad   = "2.0.0"
    }

    object libs {
        object firebase{
            const val firebaseui = "com.firebaseui:firebase-ui-storage:4.3.2"

        }
        object androidx {
            object appcompat {
                const val appcompat = "androidx.appcompat:appcompat:1.4.2"
                const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
                const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
            }
            object leanback {
                const val leanback = "androidx.leanback:leanback:${versions.leanback}"
                const val leanbackPreference = "androidx.leanback:leanback-preference:${versions.leanback}"
                const val leanbackPaging = "androidx.leanback:leanback-paging:1.1.0-alpha07"
                const val tvProvider = "androidx.tvprovider:tvprovider:1.0.0"
            }
            object ktx {
                const val core = "androidx.core:core-ktx:1.8.0"
                const val coreKtx = "androidx.core:core-ktx:1.8.0"
                const val collection = "androidx.collection:collection-ktx:1.1.0"
            }
            object lifecycle {
                const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:${versions.lifecycle}"
                const val processor = "androidx.lifecycle:lifecycle-compiler:${versions.lifecycle}"
                const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${versions.lifecycle}"
                const val reactiveStreams = "android.arch.lifecycle:reactivestreams:1.1.1"
                const val lifescope = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"
                const val playservices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1"
            }
            object preferences {
                const val preferencesKtx = "androidx.preference:preference-ktx:1.1.1"
            }
            object paging {
                const val common = "androidx.paging:paging-common:${versions.paging}"
                const val runtime = "androidx.paging:paging-runtime:${versions.paging}"
            }
            object navigation {
                const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${versions.navigation}"
                const val navigationUi = "androidx.navigation:navigation-ui-ktx:${versions.navigation}"
            }
            object room {
                const val common = "androidx.room:room-common:${versions.room}"
                const val compiler = "androidx.room:room-compiler:${versions.room}"
                const val runtime = "androidx.room:room-runtime:${versions.room}"
                const val paging = "androidx.room:room-paging:${versions.room}"
                const val ktx = "androidx.room:room-ktx:${versions.room}"
            }
            object fragment {
                const val fragment = "androidx.fragment:fragment:${versions.fragment}"
                const val ktx = "androidx.fragment:fragment-ktx:${versions.fragment}"
            }
            const val documentfile = "androidx.documentfile:documentfile:1.0.1"
            object activity {
                const val activity = "androidx.activity:activity:${versions.activity}"
                const val activityKtx = "androidx.activity:activity-ktx:${versions.activity}"
            }
        }
        object arch {
            object work {
                const val runtime = "androidx.work:work-runtime:${versions.work}"
                const val runtimeKtx = "androidx.work:work-runtime-ktx:${versions.work}"
            }
        }
        object dagger {
            const val core = "com.google.dagger:dagger:${versions.dagger}"
            const val compiler = "com.google.dagger:dagger-compiler:${versions.dagger}"
            object android {
                const val core = "com.google.dagger:dagger-android:${versions.dagger}"
                const val processor = "com.google.dagger:dagger-android-processor:${versions.dagger}"
                const val support = "com.google.dagger:dagger-android-support:${versions.dagger}"
            }
        }
        object kotlin {
            const val stdlib = "stdlib"
            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-core:${versions.serialization}"
            const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${versions.serialization}"
        }
        object epoxy {
            const val expoxy = "com.airbnb.android:epoxy:${versions.epoxy}"
            const val paging = "com.airbnb.android:epoxy-paging:${versions.epoxy}"
            const val processor = "com.airbnb.android:epoxy-processor:${versions.epoxy}"
        }
        object play {
            const val review = "com.google.android.play:review:2.0.0"
            const val reviewKtx = "com.google.android.play:review-ktx:2.0.0"
            const val featureDelivery = "com.google.android.play:feature-delivery:2.0.0"
            const val featureDeliveryKtx = "com.google.android.play:feature-delivery-ktx:2.0.0"
            const val playServices = "com.google.android.gms:play-services-auth:17.0.0"
            const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4"
        }
        object gdrive {
            const val apiClient            = "com.google.api-client:google-api-client:${versions.googleApiClient}"
            const val apiClientAndroid     = "com.google.api-client:google-api-client-android:${versions.googleApiClient}"
            const val apiServicesDrive     = "com.google.apis:google-api-services-drive:v3-rev20210725-${versions.googleApiClient}"
        }

        const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
        const val ktlint                   = "com.github.shyiko:ktlint:0.29.0"
        const val okio                     = "com.squareup.okio:okio:2.10.0"
        const val okHttp3                  = "com.squareup.okhttp3:okhttp:${versions.okHttp}"
        const val coil                     = "io.coil-kt:coil:1.4.0"
        const val retrofit                 = "com.squareup.retrofit2:retrofit:${versions.retrofit}"
        const val flowPreferences          = "com.fredporciuncula:flow-preferences:1.8.0"
        const val timber                   = "com.jakewharton.timber:timber:5.0.1"
        const val material                 = "com.google.android.material:material:1.9.0-beta01"
        const val multitouchGestures       = "com.dinuscxj:multitouchgesturedetector:1.0.0"
        const val guava                    = "com.google.guava:guava:30.1.1-android"
        const val harmony                  = "com.frybits.harmony:harmony:1.1.9"
        const val startup                  = "androidx.startup:startup-runtime:1.1.1"
        const val radialgamepad            = "com.github.Swordfish90:RadialGamePad:${versions.radialgamepad}"
        const val libretrodroid            = "com.github.Swordfish90:LibretroDroid:${versions.libretrodroid}"
    }

    object plugins {
        const val android = "com.android.tools.build:gradle:7.1.3"
        const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${versions.navigation}"
    }
}
