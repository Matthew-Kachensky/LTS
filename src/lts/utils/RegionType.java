package lts.utils;

public enum RegionType {
    DEMACIA("Demacia"),
    NOXUS("Noxus"),
    FRELJORD("Freljord"),
    VOID("Void"),
    IONIA("Ionia"),
    BANDLE("Bandle City"),
    NONE("No Region");

    private String regionName;

    RegionType(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public static RegionType fromString(String input) {
        for (RegionType regionType : RegionType.values()) {
            if (regionType.regionName.equalsIgnoreCase(input)) {
                return regionType;
            }
        }
        // Default to NONE if no match is found
        return NONE;
    }

}
