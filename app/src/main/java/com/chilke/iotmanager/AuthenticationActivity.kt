package com.chilke.iotmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.*
import com.chilke.iotmanager.network.IotDeviceManager

class AuthenticationActivity : AppCompatActivity() {
    companion object {
        private val TAG = AuthenticationActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        java.util.logging.Logger.getLogger("com.amazonaws").setLevel(java.util.logging.Level.FINEST);
        setContentView(R.layout.activity_authentication)
//        Log.d(TAG, "Before init:")
//        IotDeviceManager.logCredentials()
        AWSMobileClient.getInstance().initialize(this, object : Callback<UserStateDetails> {
            override fun onError(e: java.lang.Exception?) {
                Log.e(TAG, "AWSMobileClient init error:", e)
            }

            override fun onResult(result: UserStateDetails?) {
                if (result != null) {
                    Log.i(TAG, "UserState changed ${result.userState}")

                    if (result.userState == UserState.SIGNED_IN) {
                        Log.i(TAG, "Already signed in, launching main activity")
                        val i = Intent(this@AuthenticationActivity, MainActivity::class.java)
                        startActivity(i)
                    } else {
                        AWSMobileClient.getInstance().signOut()
                        try {
                            AWSMobileClient.getInstance().showSignIn(this@AuthenticationActivity,
                                SignInUIOptions.builder().nextActivity(MainActivity::class.java).build())
                        } catch (ex: Exception) {
                            Log.e(TAG, "showSignIn error:", ex)
                        }
                    }
                } else {
                    Log.e(TAG, "init result is null")
                }
            }
        })
    }
}
