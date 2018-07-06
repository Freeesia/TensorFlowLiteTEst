package com.studiofreesia.app.tensorflowlitetest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.ml.custom.FirebaseModelManager
import com.google.firebase.ml.custom.model.FirebaseCloudModelSource
import com.google.firebase.ml.custom.model.FirebaseModelDownloadConditions
import android.os.Build
import com.google.firebase.ml.custom.FirebaseModelInterpreter
import com.google.firebase.ml.custom.FirebaseModelOptions
import com.google.firebase.ml.custom.FirebaseModelDataType
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions







class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var conditionsBuilder = FirebaseModelDownloadConditions.Builder().requireWifi()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Enable advanced conditions on Android Nougat and newer.
            conditionsBuilder = conditionsBuilder
                    .requireCharging()
                    .requireDeviceIdle()
        }
        val conditions = conditionsBuilder.build()

        val cloudSource = FirebaseCloudModelSource.Builder("mobilenet_v1")
                .enableModelUpdates(true)
                .setInitialDownloadConditions(conditions)
                .setUpdatesDownloadConditions(conditions)
                .enableModelUpdates(true)
                .build()
        FirebaseModelManager.getInstance().registerCloudModelSource(cloudSource)

        val options = FirebaseModelOptions.Builder()
                .setCloudModelName("mobilenet_v1")
                .build()

        val firebaseInterpreter = FirebaseModelInterpreter.getInstance(options)

        val inputOutputOptions = FirebaseModelInputOutputOptions.Builder()
                .setInputFormat(0, FirebaseModelDataType.BYTE, intArrayOf(1, 640, 480, 3))
                .setOutputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 1000))
                .build()
    }
}
