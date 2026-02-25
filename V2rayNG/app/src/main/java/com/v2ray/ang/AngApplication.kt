package com.v2ray.ang

import android.content.Context
import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import androidx.work.WorkManager
import com.tencent.mmkv.MMKV
import com.v2ray.ang.AppConfig.ANG_PACKAGE
import com.v2ray.ang.handler.AngConfigManager
import com.v2ray.ang.handler.MmkvManager
import com.v2ray.ang.handler.SettingsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AngApplication : MultiDexApplication() {
    companion object {
        lateinit var application: AngApplication
        private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    /**
     * Attaches the base context to the application.
     * @param base The base context.
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this
    }

    private val workManagerConfiguration: Configuration = Configuration.Builder()
        .setDefaultProcessName("${ANG_PACKAGE}:bg")
        .build()

    /**
     * Initializes the application.
     */
    override fun onCreate() {
        super.onCreate()

        MMKV.initialize(this)

        // Initialize WorkManager with the custom configuration
        WorkManager.initialize(this, workManagerConfiguration)

        // Ensure critical preference defaults are present in MMKV early
        SettingsManager.initApp(this)
        SettingsManager.setNightMode()

        es.dmoral.toasty.Toasty.Config.getInstance()
            .setGravity(android.view.Gravity.BOTTOM, 0, 300)
            .apply()

        // Update built-in subscriptions on start if enabled
        updateBuiltinSubscriptionsOnStart()
    }

    /**
     * Updates built-in subscriptions on app start if configured to do so.
     */
    private fun updateBuiltinSubscriptionsOnStart() {
        if (!BuiltinSubscriptions.UPDATE_ON_START) {
            return
        }

        applicationScope.launch {
            try {
                val subscriptions = MmkvManager.decodeSubscriptions()
                for (sub in subscriptions) {
                    if (sub.subscription.url.isNotBlank() && sub.subscription.enabled) {
                        val count = AngConfigManager.updateConfigViaSub(sub)
                        if (count > 0) {
                            android.util.Log.i(AppConfig.TAG, 
                                "Updated subscription on start: ${sub.subscription.remarks}, $count configs")
                        }
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e(AppConfig.TAG, "Failed to update subscriptions on start", e)
            }
        }
    }
}
