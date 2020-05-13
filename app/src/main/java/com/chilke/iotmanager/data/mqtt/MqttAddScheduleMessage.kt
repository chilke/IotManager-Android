package com.chilke.iotmanager.data.mqtt

import com.chilke.iotmanager.data.IotSchedule
import com.google.gson.annotations.SerializedName

class MqttAddScheduleMessage: MqttMessage() {
    @SerializedName("sc") var schedule: IotSchedule? = null
    init { command = "add_schedule" }
}