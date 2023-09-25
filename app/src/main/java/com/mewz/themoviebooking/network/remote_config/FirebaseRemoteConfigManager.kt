package com.mewz.themoviebooking.network.remote_config

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

@SuppressLint("StaticFieldLeak")
object FirebaseRemoteConfigManager {

    private var remoteConfig = Firebase.remoteConfig

    init {
        val configSetting = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSetting)
    }

    fun setUpRemoteConfigWithDefaultValue(){
        val defaultValue: Map<String, Any> = hashMapOf(
            "mainScreenAppBarTitle" to "Verify Your Phone Number"
        )
        remoteConfig.setDefaultsAsync(defaultValue)
    }

    fun fetchRemoteConfigs(){
        remoteConfig.fetch()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("Firebase", "Firebase Remote Config fetch success")
                    remoteConfig.activate().addOnCompleteListener {
                        Log.d("Firebase", "Firebase Remote Config activated")
                    }
                }else {
                    Log.d("Firebase", "Firebase Remote Config fetch failed")
                }
            }
    }

    fun getLoginSentence(): String {
        return remoteConfig.getValue("mainScreenAppBarTitle").asString()
    }
}