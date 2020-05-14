package com.chilke.iotmanager.data

import com.google.gson.annotations.SerializedName

abstract class IotDeviceState {
    @SerializedName("ci") var clientID: String? = null
    @SerializedName("tp") var type: String? = null
}