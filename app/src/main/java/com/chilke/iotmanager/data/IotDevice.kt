package com.chilke.iotmanager.data

import com.google.gson.annotations.SerializedName

abstract class IotDevice {
    @SerializedName("ci") var clientID = ""
    @SerializedName("hn") var mqttHostName = ""
    @SerializedName("pt") var mqttPort = 0
    @SerializedName("lo") var location = ""
    @SerializedName("tz") var posixTimezone = ""
    @SerializedName("tp") var type = ""
}

