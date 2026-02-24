package com.v2ray.ang

import com.v2ray.ang.dto.SubscriptionItem

/**
 * Built-in subscriptions configuration.
 * These subscriptions will be automatically loaded on app start if they don't already exist.
 * 
 * To configure your subscriptions:
 * 1. Edit the BUILTIN_SUBSCRIPTIONS list below
 * 2. Each SubscriptionItem represents a subscription with its settings
 * 
 * Example:
 * SubscriptionItem(
 *     remarks = "My Subscription",       // Display name
 *     url = "https://example.com/sub",   // Subscription URL
 *     enabled = true,                     // Enable update
 *     autoUpdate = true,                  // Auto update on interval
 *     filter = ".*",                      // Regex filter for server names (optional)
 *     allowInsecureUrl = false,           // Allow HTTP URLs
 *     userAgent = null                    // Custom user agent (optional)
 * )
 */
object BuiltinSubscriptions {

    /**
     * List of built-in subscriptions to load on app start.
     * Edit this list to configure your default subscriptions.
     * 
     * These subscriptions will be added if they don't already exist (by URL).
     */
    val BUILTIN_SUBSCRIPTIONS: List<SubscriptionItem> = listOf(
        // Example subscription (uncomment and modify as needed):
        // SubscriptionItem(
        //     remarks = "My VPN Subscription",
        //     url = "https://your-subscription-url-here",
        //     enabled = true,
        //     autoUpdate = true,
        //     allowInsecureUrl = false
        // ),
        
        // Add more subscriptions here...
    )

    /**
     * Whether to update built-in subscriptions on app start.
     * Set to true to automatically fetch servers when the app launches.
     */
    const val UPDATE_ON_START: Boolean = true

    /**
     * Whether to force update even if subscription already exists.
     * If false, subscriptions with matching URLs won't be re-added.
     * If true, existing subscriptions with matching URLs will be updated.
     */
    const val FORCE_UPDATE_EXISTING: Boolean = false
}
