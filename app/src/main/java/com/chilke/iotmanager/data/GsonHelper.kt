package com.chilke.iotmanager.data

import com.chilke.iotmanager.data.mqtt.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory

object GsonHelper {
    fun create(): Gson {
        val devAdapter = RuntimeTypeAdapterFactory.of(IotDevice::class.java, "tp", true)
            .registerSubtype(IotDimmer::class.java, "Dim")
        val mqttAdapter = RuntimeTypeAdapterFactory.of(MqttMessage::class.java, "cmd", true)
            .registerSubtype(MqttGetDevicesMessage::class.java, "get_devices")
            .registerSubtype(MqttDeviceStateMessage::class.java, "device_state")
            .registerSubtype(MqttDeviceInfoMessage::class.java, "device_info")
            .registerSubtype(MqttGetSchedulesMessage::class.java, "get_schedules")
            .registerSubtype(MqttSchedulesMessage::class.java, "schedules")
            .registerSubtype(MqttAddScheduleMessage::class.java, "add_schedule")
            .registerSubtype(MqttDeleteScheduleMessage::class.java, "del_schedule")
        val stateAdapter = RuntimeTypeAdapterFactory.of(IotDeviceState::class.java, "tp", true)
            .registerSubtype(IotDimmerState::class.java, "Dim")
        val gson = GsonBuilder().registerTypeAdapterFactory(devAdapter)
            .registerTypeAdapterFactory(mqttAdapter)
            .registerTypeAdapterFactory(stateAdapter).create()

        return gson
    }
}