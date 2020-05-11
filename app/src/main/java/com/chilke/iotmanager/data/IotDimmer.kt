package com.chilke.iotmanager.data

import com.google.gson.annotations.SerializedName

class IotDimmer : IotDevice() {
    @SerializedName("ch") val channels = Array<IotDimmerChannel>(2) { IotDimmerChannel() }

    init {
        type = "Dim"
    }
}