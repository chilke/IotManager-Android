package com.chilke.iotmanager.data.mqtt

import com.google.gson.annotations.SerializedName

class MqttDeleteScheduleMessage: MqttMessage() {
    @SerializedName("id") var id = 0
    init { command = "del_message" }
}