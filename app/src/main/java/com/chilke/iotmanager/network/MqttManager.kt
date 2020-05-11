package com.chilke.iotmanager.network

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.iot.*
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.iot.AWSIotClient
import com.amazonaws.services.iot.model.AttachPolicyRequest
import com.amazonaws.services.iot.model.ListAttachedPoliciesRequest
import com.chilke.iotmanager.data.mqtt.MqttGetDevicesMessage
import com.chilke.iotmanager.data.mqtt.MqttMessage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.eclipse.paho.client.mqttv3.MqttException
import java.util.*

object MqttManager {
    private val TAG = MqttManager::class.simpleName

    private const val IOT_POLICY_NAME = "IotManagerApp"
    private const val IOT_ENDPOINT = "a1r32q860r2zlh-ats.iot.us-east-2.amazonaws.com"
    private const val RECV_TOPIC = "to/apps"
    private const val BROADCAST_TOPIC = "to/devices"

    private val REGION = Regions.US_EAST_2
    private val clientID = UUID.randomUUID().toString()
    private lateinit var awsMqttManager: AWSIotMqttManager

    private val gson: Gson
    init {
        gson = GsonBuilder().create()
    }

    private val mqttSubscriptionCallback = object: AWSIotMqttSubscriptionStatusCallback {
        override fun onFailure(exception: Throwable?) {
            Log.e(TAG, "Subscribe failed: ", exception)
        }

        override fun onSuccess() {
            Log.i(TAG, "Subscribe successful, publishing message")
            sendMessage(MqttGetDevicesMessage())
        }
    }

    private val mqttMessageCallback = object : AWSIotMqttNewMessageCallback {
        override fun onMessageArrived(topic: String?, data: ByteArray?) {
            try {
                val message = String(data!!)
                Log.i(TAG, "MQTT message received on topic: $topic\n$message")
            } catch (ex: Exception) {
                Log.e(TAG, "MQTT message receive exception: ", ex)
            }
        }
    }

    private val mqttStatusCallback = object : AWSIotMqttClientStatusCallback {
        override fun onStatusChanged(
            status: AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus?,
            throwable: Throwable?
        ) {
            if (throwable != null) {
                Log.e(TAG, "MQTT onStatusChanged exception: ", throwable)

                if (throwable is MqttException) {
                    val iotClient = AWSIotClient(AWSMobileClient.getInstance())
                    iotClient.setRegion(Region.getRegion(REGION))
                    Log.d(TAG, "Getting attached policies")
                    val listReq = ListAttachedPoliciesRequest()
                    listReq.target = AWSMobileClient.getInstance().identityId
                    val listRes = iotClient.listAttachedPolicies(listReq)
                    var hasPolicy = false
                    if (listRes.policies != null && !listRes.policies.isEmpty()) {
                        for (policy in listRes.policies) {
                            Log.d(TAG, "Policy arn: ${policy.policyArn}")
                            Log.d(TAG, "Policy name: ${policy.policyName}")
                            if (policy.policyName == IOT_POLICY_NAME) {
                                hasPolicy = true
                            }
                        }
                    }

                    if (!hasPolicy) {
                        //Attach policy here
                        Log.d(TAG, "Policies is empty")
                        val attachReq = AttachPolicyRequest()
                        attachReq.target = AWSMobileClient.getInstance().identityId
                        attachReq.policyName = IOT_POLICY_NAME
                        iotClient.attachPolicy(attachReq)

                        scheduleReconnect(5000)
                    } else {
                        scheduleReconnect(60000)
                    }
                } else {
                    scheduleReconnect(60000)
                }
            } else if (status != null) {
                if (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected) {
                    Log.i(TAG, "Connected, subscribing")
                    awsMqttManager.subscribeToTopic(
                        RECV_TOPIC, AWSIotMqttQos.QOS0,
                        mqttSubscriptionCallback, mqttMessageCallback
                    )
                } else if (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.ConnectionLost) {
                    scheduleReconnect(5000)
                }
            } else {
                Log.w(TAG, "MQTT onStatusChanged, status is null")
                scheduleReconnect(60000)
            }
        }
    }

    private fun scheduleReconnect(millis: Long) {
        Log.i(TAG, "Scheduling reconnect in ${millis}ms")
        val ht = HandlerThread("Reconnect thread")
        ht.start()
        val looper = ht.looper
        val handler = Handler(looper)
        handler.postDelayed({
            mqttConnect()
        }, millis)
    }

    fun init() {
        awsMqttManager = AWSIotMqttManager(clientID, IOT_ENDPOINT)
        awsMqttManager.keepAlive = 45
        awsMqttManager.isAutoReconnect = false

        mqttConnect()
    }

    fun mqttConnect() {
        try {
            awsMqttManager.connect(AWSMobileClient.getInstance(), mqttStatusCallback)
        } catch (ex: Exception) {
            Log.e(TAG, "MQTT Connect Exception: ", ex)
        }
    }

    fun sendMessage(message: MqttMessage) {
        awsMqttManager.publishString(Gson().toJson(message), BROADCAST_TOPIC, AWSIotMqttQos.QOS0)
    }
}