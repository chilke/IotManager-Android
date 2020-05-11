package com.chilke.iotmanager.network

import android.util.Log
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.iot.AWSIotClient
import com.amazonaws.services.iot.model.AttachPolicyRequest
import com.amazonaws.services.iot.model.ListAttachedPoliciesRequest
import com.chilke.iotmanager.data.IotDevice
import java.util.*
import kotlin.concurrent.thread

object IotDeviceManager {
    private val TAG = IotDeviceManager::class.simpleName

    val iotDevices = ArrayList<IotDevice>()

    fun init() {
        MqttManager.init()
    }

    fun refreshDevices() {

    }
}