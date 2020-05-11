package com.chilke.iotmanager.data.mqtt

class MqttGetSchedulesMessage: MqttMessage() {
    init { command = "get_schedules" }
}