package com.chilke.iotmanager.data.mqtt

class MqttGetDevicesMessage: MqttMessage() {
    init { command = "get_devices" }
}