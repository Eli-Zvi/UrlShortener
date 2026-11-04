package com.eli.urlshortener.urlshortener.googlesafebrowsing;

public class ThreatOptions {

    public record ThreatEntry(String url){
    }

    /**
     * Represents a Google Safe Browsing ThreatType Enum
     * <a href="https://developers.google.com/safe-browsing/v4/reference/rest/v4/ThreatType">Reference</a>
     */
    public enum ThreatType {
        MALWARE("MALWARE"),
        SOCIAL_ENGINEERING("SOCIAL_ENGINEERING"),
        UNWANTED_SOFTWARE("UNWANTED_SOFTWARE"),
        POTENTIALLY_HARMFUL_APPLICATION("POTENTIALLY_HARMFUL_APPLICATION"),
        THREAT_TYPE_UNSPECIFIED("THREAT_TYPE_UNSPECIFIED");

        public final String threat;

        ThreatType(String threat){
            this.threat = threat;
        }
    }

    /**
     * Represents a Google Safe Browsing PlatformTypes Enum
     * <a href="https://developers.google.com/safe-browsing/reference/rest/v4/PlatformType">Reference</a>
     */
    public enum PlatformType {
        WINDOWS("WINDOWS"),
        LINUX("LINUX"),
        ANY_PLATFORM("ANY_PLATFORM"),
        CHROME("CHROME"),
        ALL_PLATFORMS("ALL_PLATFORMS"),
        IOS("IOS"),
        OSX("OSX"),
        ANDROID("ANDROID"),
        PLATFORM_TYPE_UNSPECIFIED("PLATFORM_TYPE_UNSPECIFIED");

        public final String platform;

        PlatformType(String platform){
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
