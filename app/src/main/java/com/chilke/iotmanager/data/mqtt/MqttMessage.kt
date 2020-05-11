package com.chilke.iotmanager.data.mqtt

import com.google.gson.annotations.SerializedName

open class MqttMessage {
    @SerializedName("cmd") var command = ""
}