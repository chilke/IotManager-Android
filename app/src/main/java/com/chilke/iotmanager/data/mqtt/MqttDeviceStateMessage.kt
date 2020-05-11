package com.chilke.iotmanager.data.mqtt

import com.chilke.iotmanager.data.IotDeviceState
import com.google.gson.annotations.SerializedName

class MqttDeviceStateMessage: MqttMessage() {
    @SerializedName("st") var state: IotDeviceState? = null
    init { command = "device_state" }
}