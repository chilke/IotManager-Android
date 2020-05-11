package com.chilke.iotmanager.data

import com.google.gson.annotations.SerializedName

class IotDimmerState: IotDeviceState() {
    @SerializedName("cvs") val curValues = Array<Int>(2) {0}
}