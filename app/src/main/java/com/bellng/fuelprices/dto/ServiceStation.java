package com.bellng.fuelprices.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ServiceStation {

    @SerializedName("ServiceStationID")
    @Expose
    private Integer serviceStationID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Lat")
    @Expose
    private Double lat;
    @SerializedName("Long")
    @Expose
    private Double _long;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Brand")
    @Expose
    private String brand;
    @SerializedName("Distance")
    @Expose
    private Double distance;
    @SerializedName("IsAlternate")
    @Expose
    private Boolean isAlternate;
    @SerializedName("Prices")
    @Expose
    private List<Price> prices = new ArrayList<Price>();

    /**
     * @return The serviceStationID
     */
    public Integer getServiceStationID() {
        return serviceStationID;
    }

    /**
     * @param serviceStationID The ServiceStationID
     */
    public void setServiceStationID(Integer serviceStationID) {
        this.serviceStationID = serviceStationID;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The lat
     */
    public Double getLat() {
        return lat;
    }


    /**
     * @return The _long
     */
    public Double getLong() {
        return _long;
    }


    /**
     * @return The price
     */
    public Double getPrice() {
        return price;
    }


    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }



    /**
     * @return The brand
     */
    public String getBrand() {
        return brand;
    }


    /**
     * @return The distance
     */
    public Double getDistance() {
        return distance;
    }


    /**
     * @return The isAlternate
     */
    public Boolean getIsAlternate() {
        return isAlternate;
    }


    /**
     * @return The prices
     */
    public List<Price> getPrices() {
        return prices;
    }


}