package com.bellng.fuelprices.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Price {

    @SerializedName("FuelType")
    @Expose
    private String fuelType;
    @SerializedName("Price")
    @Expose
    private Double price;

    /**
     * @return The fuelType
     */
    public String getFuelType() {
        return fuelType;
    }

    /**
     * @return The price
     */
    public Double getPrice() {
        return price;
    }


}