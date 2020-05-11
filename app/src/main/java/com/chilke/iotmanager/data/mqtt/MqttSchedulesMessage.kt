package com.chilke.iotmanager.data.mqtt

import com.chilke.iotmanager.data.IotSchedule
import com.google.gson.annotations.SerializedName

class MqttSchedulesMessage: MqttMessage() {
    @SerializedName("sch") val schedules = ArrayList<IotSchedule>()
    init { command = "schedules" }
}