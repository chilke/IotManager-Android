package com.chilke.iotmanager.data.mqtt

import com.chilke.iotmanager.data.IotSchedule
import com.google.gson.annotations.SerializedName

class MqttAddScheduleMessage: MqttMessage() {
    @SerializedName("sch") var schedule: IotSchedule? = null
    init { command = "add_schedule" }
}