package com.chilke.iotmanager.data.mqtt

import com.chilke.iotmanager.data.IotSchedule
import com.google.gson.annotations.SerializedName

class MqttSchedulesMessage: MqttMessage() {
    @SerializedName("sc") val schedules = ArrayList<IotSchedule>()
    @SerializedName("ci") var clientID = ""
    init { command = "schedules" }
}