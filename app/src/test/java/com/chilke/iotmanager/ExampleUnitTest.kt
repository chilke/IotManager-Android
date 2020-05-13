package com.chilke.iotmanager

import com.chilke.iotmanager.data.*
import com.chilke.iotmanager.data.mqtt.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun deserialize_message() {
        val gson = GsonHelper.create()
        val json1 = """{"dev":{"ch":[{"id":0,"nm":"Fan Light","mn":0,"mx":8191,"cv":1234},{"id":1,"nm":"Fan","mn":2048,"mx":8191,"cv":0}],"ci":"IOT-ABCDEF10","hn":"Host","pt":12345,"lo":"Living Room","tz":"CST6CDT","tp":"Dim"},"cmd":"device_info"}"""
        val msg1 = gson.fromJson(json1, MqttMessage::class.java)

        println(msg1::class.simpleName)
        if (msg1 is MqttDeviceInfoMessage) {
            val dev = msg1.device
            if (dev != null) {
                println(dev::class.simpleName)
            }
        }

        println(gson.toJson(msg1))

        val json2 = """{"cmd":"schedules","sch":[{"ds":[0,1,3,4],"hr":22,"mn":50,"id":0,"st":{"tp":"Dim","cvs":[1234,4321]}},{"mo":1,"yr":2020,"dy":31,"hr":22,"mn":50,"id":0,"st":{"tp":"Dim","cvs":[1234,4321]}},{"ds":[2,5],"hr":22,"mn":50,"id":0,"st":{"tp":"Dim","cvs":[1234,4321]}},{"ds":[6],"hr":22,"mn":50,"id":0,"st":{"tp":"Dim","cvs":[1234,4321]}},{"mo":2,"yr":2020,"dy":29,"hr":22,"mn":50,"id":0,"st":{"tp":"Dim","cvs":[1234,4321]}}]}"""
        val msg2 = gson.fromJson(json2, MqttMessage::class.java)

        println(msg2::class.simpleName)
        if (msg2 is MqttSchedulesMessage) {
            println(msg2.schedules.size)

        }

        val json3 = gson.toJson(msg2)
        println(json3)
    }

    @Test
    fun serialize_message() {
        var msg: MqttMessage? = null
        msg = MqttGetDevicesMessage()
        println(Gson().toJson(msg))

        val devInfoMsg = MqttDeviceInfoMessage()
        val dimDev = IotDimmer()
        dimDev.mqttHostName = "Host"
        dimDev.clientID = "IOT-ABCDEF10"
        dimDev.location = "Living Room"
        dimDev.mqttPort = 12345
        dimDev.posixTimezone = "CST6CDT"
        dimDev.channels[0].curValue = 1234
        dimDev.channels[0].id = 0
        dimDev.channels[0].maxValue = 8191
        dimDev.channels[0].minValue = 0
        dimDev.channels[0].name = "Fan Light"
        dimDev.channels[1].curValue = 0
        dimDev.channels[1].id = 1
        dimDev.channels[1].maxValue = 8191
        dimDev.channels[1].minValue = 2048
        dimDev.channels[1].name = "Fan"

        devInfoMsg.device = dimDev

        msg = devInfoMsg

        println(Gson().toJson(msg))

        val devStMsg = MqttDeviceStateMessage()
        val devSt = IotDimmerState()
        devSt.curValues[0] = 1024
        devSt.curValues[1] = 2048
        devStMsg.state = devSt
        msg = devStMsg
        println(Gson().toJson(msg))

        val addScMsg = MqttAddScheduleMessage()
        val sch = IotSchedule()

        addScMsg.schedule = sch
        msg = addScMsg
        println(Gson().toJson(msg))
    }
}
