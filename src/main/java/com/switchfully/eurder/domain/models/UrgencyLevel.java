package com.switchfully.eurder.domain.models;

public enum UrgencyLevel {

    // The order of the enum values is important.
    // They are ordered from the lowest to the highest maximumStock value.

    STOCK_LOW(5),
    STOCK_MEDIUM(10),
    STOCK_HIGH(null);

    private final Integer maximumStock;

    UrgencyLevel(Integer maximumStock) {
        this.maximumStock = maximumStock;
    }

    public static UrgencyLevel getUrgencyLevelForStockAmount(int stockAmount){
        for (UrgencyLevel urgencyLevel :
                UrgencyLevel.values()) {
            if(urgencyLevel.maximumStock != null && stockAmount < urgencyLevel.maximumStock){
                return urgencyLevel;
            }
        }
        return UrgencyLevel.values()[UrgencyLevel.values().length-1];
    }
}
