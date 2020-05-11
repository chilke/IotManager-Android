package com.chilke.iotmanager.data

import com.google.gson.annotations.SerializedName

abstract class IotDeviceState {
    @SerializedName("tp") var type = ""
}