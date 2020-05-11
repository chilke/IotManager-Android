package com.chilke.iotmanager.data

import com.google.gson.annotations.SerializedName

class IotDimmerChannel {
    @SerializedName("id") var id = 0
    @SerializedName("nm") var name = ""
    @SerializedName("mn") var minValue = 0
    @SerializedName("mx") var maxValue = 0
    @SerializedName("cv") var curValue = 0
}