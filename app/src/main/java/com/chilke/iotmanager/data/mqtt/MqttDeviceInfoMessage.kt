package com.chilke.iotmanager.data.mqtt

import com.chilke.iotmanager.data.IotDevice
import com.google.gson.annotations.SerializedName

class MqttDeviceInfoMessage: MqttMessage() {
    @SerializedName("dv") var device: IotDevice? = null

    init { command = "device_info" }
}