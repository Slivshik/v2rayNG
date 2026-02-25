package com.v2ray.ang.enums

import com.v2ray.ang.AppConfig

/**
 * Predefined ruleset modes with associated DNS and test URL configurations.
 * Each mode has specific DNS servers and routing behavior.
 */
enum class RulesetMode(
    val displayNameRes: String,
    val routingType: RoutingType,
    val remoteDns: String,
    val domesticDns: String,
    val vpnDns: String,
    val testUrl: String = AppConfig.TEST_URL_GSTATIC
) {
    /**
     * Whitelist mode for Russia:
     * - Russian sites go direct (bypass)
     * - Yandex DNS for domestic resolution
     * - Cloudflare for proxy resolution
     */
    WHITELIST_RU(
        displayNameRes = "ruleset_mode_whitelist_ru",
        routingType = RoutingType.WHITE_RU,
        remoteDns = AppConfig.DNS_PROXY,  // Cloudflare 1.1.1.1
        domesticDns = AppConfig.DNS_YANDEX,  // Yandex DNS
        vpnDns = AppConfig.DNS_YANDEX
    ),

    /**
     * Block mode (adblocking + full proxy):
     * - Blocks ads, malware, phishing
     * - Google/OpenDNS for all DNS
     * - Most traffic goes through proxy
     */
    BLOCK(
        displayNameRes = "ruleset_mode_block",
        routingType = RoutingType.BLOCK,
        remoteDns = "${AppConfig.DNS_GOOGLE_ADDRESSES[0]},${AppConfig.DNS_OPENDNS_ADDRESSES[0]}",  // Google + OpenDNS
        domesticDns = AppConfig.DNS_PROXY,
        vpnDns = AppConfig.DNS_PROXY
    );

    companion object {
        /**
         * Get the default mode (Whitelist Russia)
         */
        val DEFAULT = WHITELIST_RU

        /**
         * Get mode by ordinal
         */
        fun fromOrdinal(ordinal: Int): RulesetMode {
            return values().getOrNull(ordinal) ?: DEFAULT
        }
    }
}

/**
 * DNS addresses for different providers
 */
object DnsAddresses {
    val OPENDNS_ADDRESSES = arrayListOf(
        "208.67.222.222",
        "208.67.220.220",
        "2620:119:35::35",
        "2620:119:53::53"
    )
    
    val YANDEX_ADDRESSES = arrayListOf(
        "77.88.8.8",
        "77.88.8.1",
        "2a02:6b8::feed:0ff",
        "2a02:6b8:0:1::feed:0ff"
    )
}
