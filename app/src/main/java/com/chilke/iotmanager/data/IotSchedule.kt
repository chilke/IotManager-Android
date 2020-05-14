package com.chilke.iotmanager.data

import com.google.gson.annotations.SerializedName

class IotSchedule {
    @SerializedName("yr") var year: Int? = null
    @SerializedName("mo") var month: Int? = null
    @SerializedName("dy") var day: Int? = null
    @SerializedName("hr") var hour = 0
    @SerializedName("mn") var minute = 0
    @SerializedName("ds") var days: Array<Int>? = null
    @SerializedName("st") var state: IotDeviceState? = null
    @SerializedName("id") var id: Int? = null
}