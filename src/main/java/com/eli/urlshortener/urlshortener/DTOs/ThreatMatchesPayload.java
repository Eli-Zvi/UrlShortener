package com.eli.urlshortener.urlshortener.DTOs;

import java.util.List;

/**
 * This class represents a payload for the Google threatMatches.find Safe Browsing endpoint
 * <a href="https://developers.google.com/safe-browsing/reference/rest/v4/threatMatches/find#request-body">Request Body of endpoint</a>
 * @param client - the client portion of the payload
 * @param threatInfo - the threat info portion of the payload
 */
public record ThreatMatchesPayload(Client client,
                                   ThreatInfo threatInfo) {

    public record Client(String clientId,
                         String clientVersion){
    }

    public record ThreatInfo(List<ThreatTypes> threatTypes,
                             List<PlatformTypes> platformTypes,
                             List<ThreatEntryType> threatEntryTypes,
                             List<String> threatEntries
                             ){
    }

    /**
     * Represents a Google Safe Browsing ThreatType Enum
     * <a href="https://developers.google.com/safe-browsing/v4/reference/rest/v4/ThreatType">Reference</a>
     */
    public enum ThreatTypes{
        MALWARE("MALWARE"),
        SOCIAL_ENGINEERING("SOCIAL_ENGINEERING"),
        UNWANTED_SOFTWARE("UNWANTED_SOFTWARE"),
        POTENTIALLY_HARMFUL_APPLICATION("POTENTIALLY_HARMFUL_APPLICATION"),
        THREAT_TYPE_UNSPECIFIED("THREAT_TYPE_UNSPECIFIED");

        public final String threat;

        ThreatTypes(String threat){
            this.threat = threat;
        }
    }

    /**
     * Represents a Google Safe Browsing PlatformTypes Enum
     * <a href="https://developers.google.com/safe-browsing/reference/rest/v4/PlatformType">Reference</a>
     */
    public enum PlatformTypes{
        WINDOWS("WINDOWS"),
        LINUX("LINUX"),
        ANY("ANY_PLATFORM"),
        CHROME("CHROME"),
        ALL("ALL_PLATFORMS"),
        IOS("IOS"),
        OSX("OSX"),
        ANDROID("ANDROID"),
        PLATFORM_TYPE_UNSPECIFIED("PLATFORM_TYPE_UNSPECIFIED");

        public final String platform;

        PlatformTypes(String platform){
            this.platform = platform;
        }
    }

    /**
     * Represents a Google Safe Browsing ThreatEntryType Enum
     * <a href="https://developers.google.com/safe-browsing/reference/rest/v4/ThreatEntryType">Reference</a>
     */
    public enum ThreatEntryType{
        URL("URL"),
        EXECUTABLE("EXECUTABLE"),
        THREAT_ENTRY_TYPE_UNSPECIFIED("THREAT_ENTRY_TYPE_UNSPECIFIED");

        public final String entryType;

        ThreatEntryType(String entryType){
            this.entryType = entryType;
        }
    }
}
