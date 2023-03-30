package com.switchfully.eurder.domain.models;

import java.util.List;

import static com.switchfully.eurder.domain.models.Feature.*;

public enum Role {
    CUSTOMER(List.of(ORDER_ITEMS, VIEW_ORDERS_REPORT)),
    ADMIN(List.of(ADD_AN_ITEM, VIEW_ALL_CUSTOMERS, VIEW_A_SINGLE_CUSTOMER, GET_ITEM_OVERVIEW));


    private final List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean hasPermission(Feature feature){
        return featureList.contains(feature);
    }
}
